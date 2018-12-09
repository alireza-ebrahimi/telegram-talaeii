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

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, Object obj, byte[] bArr, String str) {
            super(dataSource, dataSpec, 3, format, i, obj, bArr);
            this.iv = str;
        }

        protected void consume(byte[] bArr, int i) {
            this.result = Arrays.copyOf(bArr, i);
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

        public InitializationTrackSelection(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
            this.selectedIndex = indexOf(trackGroup.getFormat(0));
        }

        public int getSelectedIndex() {
            return this.selectedIndex;
        }

        public Object getSelectionData() {
            return null;
        }

        public int getSelectionReason() {
            return 0;
        }

        public void updateSelectedTrack(long j) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.selectedIndex, elapsedRealtime)) {
                int i = this.length - 1;
                while (i >= 0) {
                    if (isBlacklisted(i, elapsedRealtime)) {
                        i--;
                    } else {
                        this.selectedIndex = i;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }
    }

    public HlsChunkSource(HlsPlaylistTracker hlsPlaylistTracker, HlsUrl[] hlsUrlArr, HlsDataSourceFactory hlsDataSourceFactory, TimestampAdjusterProvider timestampAdjusterProvider, List<Format> list) {
        this.playlistTracker = hlsPlaylistTracker;
        this.variants = hlsUrlArr;
        this.timestampAdjusterProvider = timestampAdjusterProvider;
        this.muxedCaptionFormats = list;
        Format[] formatArr = new Format[hlsUrlArr.length];
        int[] iArr = new int[hlsUrlArr.length];
        for (int i = 0; i < hlsUrlArr.length; i++) {
            formatArr[i] = hlsUrlArr[i].format;
            iArr[i] = i;
        }
        this.mediaDataSource = hlsDataSourceFactory.createDataSource(1);
        this.encryptionDataSource = hlsDataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(formatArr);
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, iArr);
    }

    private void clearEncryptionData() {
        this.encryptionKeyUri = null;
        this.encryptionKey = null;
        this.encryptionIvString = null;
        this.encryptionIv = null;
    }

    private EncryptionKeyChunk newEncryptionKeyChunk(Uri uri, String str, int i, int i2, Object obj) {
        return new EncryptionKeyChunk(this.encryptionDataSource, new DataSpec(uri, 0, -1, null, 1), this.variants[i].format, i2, obj, this.scratchSpace, str);
    }

    private void setEncryptionData(Uri uri, String str, byte[] bArr) {
        Object toByteArray = new BigInteger(Util.toLowerInvariant(str).startsWith("0x") ? str.substring(2) : str, 16).toByteArray();
        Object obj = new byte[16];
        int length = toByteArray.length > 16 ? toByteArray.length - 16 : 0;
        System.arraycopy(toByteArray, length, obj, (obj.length - toByteArray.length) + length, toByteArray.length - length);
        this.encryptionKeyUri = uri;
        this.encryptionKey = bArr;
        this.encryptionIvString = str;
        this.encryptionIv = obj;
    }

    public void getNextChunk(HlsMediaChunk hlsMediaChunk, long j, HlsChunkHolder hlsChunkHolder) {
        long j2;
        int indexOf = hlsMediaChunk == null ? -1 : this.trackGroup.indexOf(hlsMediaChunk.trackFormat);
        this.expectedPlaylistUrl = null;
        if (hlsMediaChunk == null) {
            j2 = 0;
        } else {
            j2 = Math.max(0, (this.independentSegments ? hlsMediaChunk.endTimeUs : hlsMediaChunk.startTimeUs) - j);
        }
        this.trackSelection.updateSelectedTrack(j2);
        int selectedIndexInTrackGroup = this.trackSelection.getSelectedIndexInTrackGroup();
        Object obj = indexOf != selectedIndexInTrackGroup ? 1 : null;
        HlsUrl hlsUrl = this.variants[selectedIndexInTrackGroup];
        if (this.playlistTracker.isSnapshotValid(hlsUrl)) {
            int i;
            int i2;
            HlsMediaPlaylist hlsMediaPlaylist;
            HlsUrl hlsUrl2;
            HlsMediaPlaylist playlistSnapshot = this.playlistTracker.getPlaylistSnapshot(hlsUrl);
            this.independentSegments = playlistSnapshot.hasIndependentSegmentsTag;
            if (hlsMediaChunk == null || obj != null) {
                HlsMediaPlaylist hlsMediaPlaylist2;
                HlsUrl hlsUrl3;
                if (hlsMediaChunk != null) {
                    j = this.independentSegments ? hlsMediaChunk.endTimeUs : hlsMediaChunk.startTimeUs;
                }
                if (playlistSnapshot.hasEndTag || j < playlistSnapshot.getEndTimeUs()) {
                    List list = playlistSnapshot.segments;
                    Object valueOf = Long.valueOf(j - playlistSnapshot.startTimeUs);
                    boolean z = !this.playlistTracker.isLive() || hlsMediaChunk == null;
                    int binarySearchFloor = Util.binarySearchFloor(list, valueOf, true, z) + playlistSnapshot.mediaSequence;
                    if (binarySearchFloor >= playlistSnapshot.mediaSequence || hlsMediaChunk == null) {
                        indexOf = binarySearchFloor;
                        hlsMediaPlaylist2 = playlistSnapshot;
                        hlsUrl3 = hlsUrl;
                        i = selectedIndexInTrackGroup;
                    } else {
                        hlsUrl = this.variants[indexOf];
                        hlsMediaPlaylist2 = this.playlistTracker.getPlaylistSnapshot(hlsUrl);
                        hlsUrl3 = hlsUrl;
                        i = indexOf;
                        indexOf = hlsMediaChunk.getNextChunkIndex();
                    }
                } else {
                    indexOf = playlistSnapshot.mediaSequence + playlistSnapshot.segments.size();
                    hlsMediaPlaylist2 = playlistSnapshot;
                    hlsUrl3 = hlsUrl;
                    i = selectedIndexInTrackGroup;
                }
                i2 = indexOf;
                hlsMediaPlaylist = hlsMediaPlaylist2;
                hlsUrl2 = hlsUrl3;
            } else {
                i2 = hlsMediaChunk.getNextChunkIndex();
                hlsMediaPlaylist = playlistSnapshot;
                hlsUrl2 = hlsUrl;
                i = selectedIndexInTrackGroup;
            }
            if (i2 < hlsMediaPlaylist.mediaSequence) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
            indexOf = i2 - hlsMediaPlaylist.mediaSequence;
            if (indexOf < hlsMediaPlaylist.segments.size()) {
                Segment segment = (Segment) hlsMediaPlaylist.segments.get(indexOf);
                if (segment.isEncrypted) {
                    Uri resolveToUri = UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, segment.encryptionKeyUri);
                    if (!resolveToUri.equals(this.encryptionKeyUri)) {
                        hlsChunkHolder.chunk = newEncryptionKeyChunk(resolveToUri, segment.encryptionIV, i, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData());
                        return;
                    } else if (!Util.areEqual(segment.encryptionIV, this.encryptionIvString)) {
                        setEncryptionData(resolveToUri, segment.encryptionIV, this.encryptionKey);
                    }
                } else {
                    clearEncryptionData();
                }
                Segment segment2 = hlsMediaPlaylist.initializationSegment;
                long j3 = hlsMediaPlaylist.startTimeUs + segment.relativeStartTimeUs;
                int i3 = hlsMediaPlaylist.discontinuitySequence + segment.relativeDiscontinuitySequence;
                hlsChunkHolder.chunk = new HlsMediaChunk(this.mediaDataSource, new DataSpec(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, segment.url), segment.byterangeOffset, segment.byterangeLength, null), segment2 != null ? new DataSpec(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, segment2.url), segment2.byterangeOffset, segment2.byterangeLength, null) : null, hlsUrl2, this.muxedCaptionFormats, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), j3, j3 + segment.durationUs, i2, i3, this.isTimestampMaster, this.timestampAdjusterProvider.getAdjuster(i3), hlsMediaChunk, this.encryptionKey, this.encryptionIv);
                return;
            } else if (hlsMediaPlaylist.hasEndTag) {
                hlsChunkHolder.endOfStream = true;
                return;
            } else {
                hlsChunkHolder.playlist = hlsUrl2;
                this.expectedPlaylistUrl = hlsUrl2;
                return;
            }
        }
        hlsChunkHolder.playlist = hlsUrl;
        this.expectedPlaylistUrl = hlsUrl;
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public TrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public void maybeThrowError() {
        if (this.fatalError != null) {
            throw this.fatalError;
        } else if (this.expectedPlaylistUrl != null) {
            this.playlistTracker.maybeThrowPlaylistRefreshError(this.expectedPlaylistUrl);
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            setEncryptionData(encryptionKeyChunk.dataSpec.uri, encryptionKeyChunk.iv, encryptionKeyChunk.getResult());
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean z, IOException iOException) {
        return z && ChunkedTrackBlacklistUtil.maybeBlacklistTrack(this.trackSelection, this.trackSelection.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), iOException);
    }

    public void onPlaylistBlacklisted(HlsUrl hlsUrl, long j) {
        int indexOf = this.trackGroup.indexOf(hlsUrl.format);
        if (indexOf != -1) {
            indexOf = this.trackSelection.indexOf(indexOf);
            if (indexOf != -1) {
                this.trackSelection.blacklist(indexOf, j);
            }
        }
    }

    public void reset() {
        this.fatalError = null;
    }

    public void selectTracks(TrackSelection trackSelection) {
        this.trackSelection = trackSelection;
    }

    public void setIsTimestampMaster(boolean z) {
        this.isTimestampMaster = z;
    }
}
