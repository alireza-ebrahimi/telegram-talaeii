package org.telegram.messenger.exoplayer2.source.hls;

import android.net.Uri;
import android.os.SystemClock;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.BehindLiveWindowException;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.chunk.Chunk;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;
import org.telegram.messenger.exoplayer2.source.chunk.DataChunk;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import org.telegram.messenger.exoplayer2.trackselection.BaseTrackSelection;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.UriUtil;
import org.telegram.messenger.exoplayer2.util.Util;

class HlsChunkSource {
    private final DataSource encryptionDataSource;
    private byte[] encryptionIv;
    private String encryptionIvString;
    private byte[] encryptionKey;
    private Uri encryptionKeyUri;
    private HlsUrl expectedPlaylistUrl;
    private IOException fatalError;
    private boolean independentSegments;
    private boolean isTimestampMaster;
    private final DataSource mediaDataSource;
    private final List<Format> muxedCaptionFormats;
    private final HlsPlaylistTracker playlistTracker;
    private byte[] scratchSpace;
    private final TimestampAdjusterProvider timestampAdjusterProvider;
    private final TrackGroup trackGroup;
    private TrackSelection trackSelection;
    private final HlsUrl[] variants;

    private static final class EncryptionKeyChunk extends DataChunk {
        public final String iv;
        private byte[] result;

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, byte[] scratchSpace, String iv) {
            super(dataSource, dataSpec, 3, trackFormat, trackSelectionReason, trackSelectionData, scratchSpace);
            this.iv = iv;
        }

        protected void consume(byte[] data, int limit) throws IOException {
            this.result = Arrays.copyOf(data, limit);
        }

        public byte[] getResult() {
            return this.result;
        }
    }

    public static final class HlsChunkHolder {
        public Chunk chunk;
        public boolean endOfStream;
        public HlsUrl playlist;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlist = null;
        }
    }

    private static final class InitializationTrackSelection extends BaseTrackSelection {
        private int selectedIndex;

        public InitializationTrackSelection(TrackGroup group, int[] tracks) {
            super(group, tracks);
            this.selectedIndex = indexOf(group.getFormat(0));
        }

        public void updateSelectedTrack(long bufferedDurationUs) {
            long nowMs = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.selectedIndex, nowMs)) {
                int i = this.length - 1;
                while (i >= 0) {
                    if (isBlacklisted(i, nowMs)) {
                        i--;
                    } else {
                        this.selectedIndex = i;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }

        public int getSelectedIndex() {
            return this.selectedIndex;
        }

        public int getSelectionReason() {
            return 0;
        }

        public Object getSelectionData() {
            return null;
        }
    }

    public HlsChunkSource(HlsPlaylistTracker playlistTracker, HlsUrl[] variants, HlsDataSourceFactory dataSourceFactory, TimestampAdjusterProvider timestampAdjusterProvider, List<Format> muxedCaptionFormats) {
        this.playlistTracker = playlistTracker;
        this.variants = variants;
        this.timestampAdjusterProvider = timestampAdjusterProvider;
        this.muxedCaptionFormats = muxedCaptionFormats;
        Format[] variantFormats = new Format[variants.length];
        int[] initialTrackSelection = new int[variants.length];
        for (int i = 0; i < variants.length; i++) {
            variantFormats[i] = variants[i].format;
            initialTrackSelection[i] = i;
        }
        this.mediaDataSource = dataSourceFactory.createDataSource(1);
        this.encryptionDataSource = dataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(variantFormats);
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, initialTrackSelection);
    }

    public void maybeThrowError() throws IOException {
        if (this.fatalError != null) {
            throw this.fatalError;
        } else if (this.expectedPlaylistUrl != null) {
            this.playlistTracker.maybeThrowPlaylistRefreshError(this.expectedPlaylistUrl);
        }
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public void selectTracks(TrackSelection trackSelection) {
        this.trackSelection = trackSelection;
    }

    public TrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public void reset() {
        this.fatalError = null;
    }

    public void setIsTimestampMaster(boolean isTimestampMaster) {
        this.isTimestampMaster = isTimestampMaster;
    }

    public void getNextChunk(HlsMediaChunk previous, long playbackPositionUs, HlsChunkHolder out) {
        int oldVariantIndex;
        long bufferedDurationUs;
        if (previous == null) {
            oldVariantIndex = -1;
        } else {
            oldVariantIndex = this.trackGroup.indexOf(previous.trackFormat);
        }
        this.expectedPlaylistUrl = null;
        if (previous == null) {
            bufferedDurationUs = 0;
        } else {
            bufferedDurationUs = Math.max(0, (this.independentSegments ? previous.endTimeUs : previous.startTimeUs) - playbackPositionUs);
        }
        this.trackSelection.updateSelectedTrack(bufferedDurationUs);
        int selectedVariantIndex = this.trackSelection.getSelectedIndexInTrackGroup();
        boolean switchingVariant = oldVariantIndex != selectedVariantIndex;
        HlsUrl selectedUrl = this.variants[selectedVariantIndex];
        if (this.playlistTracker.isSnapshotValid(selectedUrl)) {
            int chunkMediaSequence;
            HlsMediaPlaylist mediaPlaylist = this.playlistTracker.getPlaylistSnapshot(selectedUrl);
            this.independentSegments = mediaPlaylist.hasIndependentSegmentsTag;
            if (previous == null || switchingVariant) {
                long targetPositionUs = previous == null ? playbackPositionUs : this.independentSegments ? previous.endTimeUs : previous.startTimeUs;
                if (mediaPlaylist.hasEndTag || targetPositionUs < mediaPlaylist.getEndTimeUs()) {
                    List list = mediaPlaylist.segments;
                    Long valueOf = Long.valueOf(targetPositionUs - mediaPlaylist.startTimeUs);
                    boolean z = !this.playlistTracker.isLive() || previous == null;
                    chunkMediaSequence = Util.binarySearchFloor(list, valueOf, true, z) + mediaPlaylist.mediaSequence;
                    if (chunkMediaSequence < mediaPlaylist.mediaSequence && previous != null) {
                        selectedVariantIndex = oldVariantIndex;
                        selectedUrl = this.variants[selectedVariantIndex];
                        mediaPlaylist = this.playlistTracker.getPlaylistSnapshot(selectedUrl);
                        chunkMediaSequence = previous.getNextChunkIndex();
                    }
                } else {
                    chunkMediaSequence = mediaPlaylist.mediaSequence + mediaPlaylist.segments.size();
                }
            } else {
                chunkMediaSequence = previous.getNextChunkIndex();
            }
            if (chunkMediaSequence < mediaPlaylist.mediaSequence) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
            int chunkIndex = chunkMediaSequence - mediaPlaylist.mediaSequence;
            if (chunkIndex < mediaPlaylist.segments.size()) {
                Segment segment = (Segment) mediaPlaylist.segments.get(chunkIndex);
                if (segment.isEncrypted) {
                    Uri keyUri = UriUtil.resolveToUri(mediaPlaylist.baseUri, segment.encryptionKeyUri);
                    if (!keyUri.equals(this.encryptionKeyUri)) {
                        out.chunk = newEncryptionKeyChunk(keyUri, segment.encryptionIV, selectedVariantIndex, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData());
                        return;
                    } else if (!Util.areEqual(segment.encryptionIV, this.encryptionIvString)) {
                        setEncryptionData(keyUri, segment.encryptionIV, this.encryptionKey);
                    }
                } else {
                    clearEncryptionData();
                }
                DataSpec initDataSpec = null;
                Segment initSegment = mediaPlaylist.initializationSegment;
                if (initSegment != null) {
                    initDataSpec = new DataSpec(UriUtil.resolveToUri(mediaPlaylist.baseUri, initSegment.url), initSegment.byterangeOffset, initSegment.byterangeLength, null);
                }
                long startTimeUs = mediaPlaylist.startTimeUs + segment.relativeStartTimeUs;
                int discontinuitySequence = mediaPlaylist.discontinuitySequence + segment.relativeDiscontinuitySequence;
                out.chunk = new HlsMediaChunk(this.mediaDataSource, new DataSpec(UriUtil.resolveToUri(mediaPlaylist.baseUri, segment.url), segment.byterangeOffset, segment.byterangeLength, null), initDataSpec, selectedUrl, this.muxedCaptionFormats, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), startTimeUs, startTimeUs + segment.durationUs, chunkMediaSequence, discontinuitySequence, this.isTimestampMaster, this.timestampAdjusterProvider.getAdjuster(discontinuitySequence), previous, this.encryptionKey, this.encryptionIv);
                return;
            } else if (mediaPlaylist.hasEndTag) {
                out.endOfStream = true;
                return;
            } else {
                out.playlist = selectedUrl;
                this.expectedPlaylistUrl = selectedUrl;
                return;
            }
        }
        out.playlist = selectedUrl;
        this.expectedPlaylistUrl = selectedUrl;
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            setEncryptionData(encryptionKeyChunk.dataSpec.uri, encryptionKeyChunk.iv, encryptionKeyChunk.getResult());
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, IOException error) {
        return cancelable && ChunkedTrackBlacklistUtil.maybeBlacklistTrack(this.trackSelection, this.trackSelection.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), error);
    }

    public void onPlaylistBlacklisted(HlsUrl url, long blacklistMs) {
        int trackGroupIndex = this.trackGroup.indexOf(url.format);
        if (trackGroupIndex != -1) {
            int trackSelectionIndex = this.trackSelection.indexOf(trackGroupIndex);
            if (trackSelectionIndex != -1) {
                this.trackSelection.blacklist(trackSelectionIndex, blacklistMs);
            }
        }
    }

    private EncryptionKeyChunk newEncryptionKeyChunk(Uri keyUri, String iv, int variantIndex, int trackSelectionReason, Object trackSelectionData) {
        return new EncryptionKeyChunk(this.encryptionDataSource, new DataSpec(keyUri, 0, -1, null, 1), this.variants[variantIndex].format, trackSelectionReason, trackSelectionData, this.scratchSpace, iv);
    }

    private void setEncryptionData(Uri keyUri, String iv, byte[] secretKey) {
        String trimmedIv;
        if (Util.toLowerInvariant(iv).startsWith("0x")) {
            trimmedIv = iv.substring(2);
        } else {
            trimmedIv = iv;
        }
        byte[] ivData = new BigInteger(trimmedIv, 16).toByteArray();
        byte[] ivDataWithPadding = new byte[16];
        int offset = ivData.length > 16 ? ivData.length - 16 : 0;
        System.arraycopy(ivData, offset, ivDataWithPadding, (ivDataWithPadding.length - ivData.length) + offset, ivData.length - offset);
        this.encryptionKeyUri = keyUri;
        this.encryptionKey = secretKey;
        this.encryptionIvString = iv;
        this.encryptionIv = ivDataWithPadding;
    }

    private void clearEncryptionData() {
        this.encryptionKeyUri = null;
        this.encryptionKey = null;
        this.encryptionIvString = null;
        this.encryptionIv = null;
    }
}
