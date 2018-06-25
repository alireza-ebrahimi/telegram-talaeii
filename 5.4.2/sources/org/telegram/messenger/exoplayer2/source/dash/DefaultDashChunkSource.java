package org.telegram.messenger.exoplayer2.source.dash;

import android.os.SystemClock;
import com.google.android.gms.wallet.WalletConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ChunkIndex;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.mkv.MatroskaExtractor;
import org.telegram.messenger.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import org.telegram.messenger.exoplayer2.extractor.rawcc.RawCcExtractor;
import org.telegram.messenger.exoplayer2.source.BehindLiveWindowException;
import org.telegram.messenger.exoplayer2.source.chunk.Chunk;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkExtractorWrapper;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkHolder;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;
import org.telegram.messenger.exoplayer2.source.chunk.ContainerMediaChunk;
import org.telegram.messenger.exoplayer2.source.chunk.InitializationChunk;
import org.telegram.messenger.exoplayer2.source.chunk.MediaChunk;
import org.telegram.messenger.exoplayer2.source.chunk.SingleSampleMediaChunk;
import org.telegram.messenger.exoplayer2.source.dash.manifest.AdaptationSet;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifest;
import org.telegram.messenger.exoplayer2.source.dash.manifest.RangedUri;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Representation;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;
    private IOException fatalError;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;
    protected final RepresentationHolder[] representationHolders;
    private final TrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements org.telegram.messenger.exoplayer2.source.dash.DashChunkSource.Factory {
        private final org.telegram.messenger.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(org.telegram.messenger.exoplayer2.upstream.DataSource.Factory factory) {
            this(factory, 1);
        }

        public Factory(org.telegram.messenger.exoplayer2.upstream.DataSource.Factory factory, int i) {
            this.dataSourceFactory = factory;
            this.maxSegmentsPerLoad = i;
        }

        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection, int i2, long j, boolean z, boolean z2) {
            return new DefaultDashChunkSource(loaderErrorThrower, dashManifest, i, iArr, trackSelection, i2, this.dataSourceFactory.createDataSource(), j, this.maxSegmentsPerLoad, z, z2);
        }
    }

    protected static final class RepresentationHolder {
        final ChunkExtractorWrapper extractorWrapper;
        private long periodDurationUs;
        public Representation representation;
        public DashSegmentIndex segmentIndex;
        private int segmentNumShift;

        RepresentationHolder(long j, Representation representation, boolean z, boolean z2) {
            this.periodDurationUs = j;
            this.representation = representation;
            String str = representation.format.containerMimeType;
            if (mimeTypeIsRawText(str)) {
                this.extractorWrapper = null;
            } else {
                Extractor rawCcExtractor;
                if (MimeTypes.APPLICATION_RAWCC.equals(str)) {
                    rawCcExtractor = new RawCcExtractor(representation.format);
                } else if (mimeTypeIsWebm(str)) {
                    rawCcExtractor = new MatroskaExtractor(1);
                } else {
                    int i = 0;
                    if (z) {
                        i = 4;
                    }
                    if (z2) {
                        i |= 8;
                    }
                    Object fragmentedMp4Extractor = new FragmentedMp4Extractor(i);
                }
                this.extractorWrapper = new ChunkExtractorWrapper(rawCcExtractor, representation.format);
            }
            this.segmentIndex = representation.getIndex();
        }

        private static boolean mimeTypeIsRawText(String str) {
            return MimeTypes.isText(str) || MimeTypes.APPLICATION_TTML.equals(str);
        }

        private static boolean mimeTypeIsWebm(String str) {
            return str.startsWith(MimeTypes.VIDEO_WEBM) || str.startsWith(MimeTypes.AUDIO_WEBM) || str.startsWith(MimeTypes.APPLICATION_WEBM);
        }

        public int getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public int getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentEndTimeUs(int i) {
            return getSegmentStartTimeUs(i) + this.segmentIndex.getDurationUs(i - this.segmentNumShift, this.periodDurationUs);
        }

        public int getSegmentNum(long j) {
            return this.segmentIndex.getSegmentNum(j, this.periodDurationUs) + this.segmentNumShift;
        }

        public long getSegmentStartTimeUs(int i) {
            return this.segmentIndex.getTimeUs(i - this.segmentNumShift);
        }

        public RangedUri getSegmentUrl(int i) {
            return this.segmentIndex.getSegmentUrl(i - this.segmentNumShift);
        }

        void updateRepresentation(long j, Representation representation) {
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation.getIndex();
            this.periodDurationUs = j;
            this.representation = representation;
            if (index != null) {
                this.segmentIndex = index2;
                if (index.isExplicit()) {
                    int segmentCount = index.getSegmentCount(this.periodDurationUs);
                    if (segmentCount != 0) {
                        segmentCount = (segmentCount + index.getFirstSegmentNum()) - 1;
                        long timeUs = index.getTimeUs(segmentCount) + index.getDurationUs(segmentCount, this.periodDurationUs);
                        int firstSegmentNum = index2.getFirstSegmentNum();
                        long timeUs2 = index2.getTimeUs(firstSegmentNum);
                        if (timeUs == timeUs2) {
                            this.segmentNumShift += (segmentCount + 1) - firstSegmentNum;
                        } else if (timeUs < timeUs2) {
                            throw new BehindLiveWindowException();
                        } else {
                            this.segmentNumShift = (index.getSegmentNum(timeUs2, this.periodDurationUs) - firstSegmentNum) + this.segmentNumShift;
                        }
                    }
                }
            }
        }
    }

    public DefaultDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection, int i2, DataSource dataSource, long j, int i3, boolean z, boolean z2) {
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = dashManifest;
        this.adaptationSetIndices = iArr;
        this.trackSelection = trackSelection;
        this.trackType = i2;
        this.dataSource = dataSource;
        this.periodIndex = i;
        this.elapsedRealtimeOffsetMs = j;
        this.maxSegmentsPerLoad = i3;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i);
        List representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[trackSelection.length()];
        for (int i4 = 0; i4 < this.representationHolders.length; i4++) {
            this.representationHolders[i4] = new RepresentationHolder(periodDurationUs, (Representation) representations.get(trackSelection.getIndexInTrackGroup(i4)), z, z2);
        }
    }

    private long getNowUnixTimeUs() {
        return this.elapsedRealtimeOffsetMs != 0 ? (SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs) * 1000 : System.currentTimeMillis() * 1000;
    }

    private ArrayList<Representation> getRepresentations() {
        List list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList();
        for (int i : this.adaptationSetIndices) {
            arrayList.addAll(((AdaptationSet) list.get(i)).representations);
        }
        return arrayList;
    }

    protected static Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource, Format format, int i, Object obj, RangedUri rangedUri, RangedUri rangedUri2) {
        String str = representationHolder.representation.baseUrl;
        if (rangedUri != null) {
            rangedUri2 = rangedUri.attemptMerge(rangedUri2, str);
            if (rangedUri2 != null) {
                rangedUri = rangedUri2;
            }
        } else {
            rangedUri = rangedUri2;
        }
        return new InitializationChunk(dataSource, new DataSpec(rangedUri.resolveUri(str), rangedUri.start, rangedUri.length, representationHolder.representation.getCacheKey()), format, i, obj, representationHolder.extractorWrapper);
    }

    protected static Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource, int i, Format format, int i2, Object obj, int i3, int i4) {
        Representation representation = representationHolder.representation;
        long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(i3);
        RangedUri segmentUrl = representationHolder.getSegmentUrl(i3);
        String str = representation.baseUrl;
        if (representationHolder.extractorWrapper == null) {
            return new SingleSampleMediaChunk(dataSource, new DataSpec(segmentUrl.resolveUri(str), segmentUrl.start, segmentUrl.length, representation.getCacheKey()), format, i2, obj, segmentStartTimeUs, representationHolder.getSegmentEndTimeUs(i3), i3, i, format);
        }
        int i5 = 1;
        int i6 = 1;
        while (i6 < i4) {
            RangedUri attemptMerge = segmentUrl.attemptMerge(representationHolder.getSegmentUrl(i3 + i6), str);
            if (attemptMerge == null) {
                break;
            }
            i5++;
            i6++;
            segmentUrl = attemptMerge;
        }
        long segmentEndTimeUs = representationHolder.getSegmentEndTimeUs((i3 + i5) - 1);
        return new ContainerMediaChunk(dataSource, new DataSpec(segmentUrl.resolveUri(str), segmentUrl.start, segmentUrl.length, representation.getCacheKey()), format, i2, obj, segmentStartTimeUs, segmentEndTimeUs, i3, i5, -representation.presentationTimeOffsetUs, representationHolder.extractorWrapper);
    }

    public void getNextChunk(MediaChunk mediaChunk, long j, ChunkHolder chunkHolder) {
        if (this.fatalError == null) {
            this.trackSelection.updateSelectedTrack(mediaChunk != null ? mediaChunk.endTimeUs - j : 0);
            RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.getSelectedIndex()];
            if (representationHolder.extractorWrapper != null) {
                Representation representation = representationHolder.representation;
                RangedUri rangedUri = null;
                RangedUri rangedUri2 = null;
                if (representationHolder.extractorWrapper.getSampleFormats() == null) {
                    rangedUri = representation.getInitializationUri();
                }
                if (representationHolder.segmentIndex == null) {
                    rangedUri2 = representation.getIndexUri();
                }
                if (!(rangedUri == null && rangedUri2 == null)) {
                    chunkHolder.chunk = newInitializationChunk(representationHolder, this.dataSource, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), rangedUri, rangedUri2);
                    return;
                }
            }
            long nowUnixTimeUs = getNowUnixTimeUs();
            int segmentCount = representationHolder.getSegmentCount();
            boolean z;
            if (segmentCount == 0) {
                z = !this.manifest.dynamic || this.periodIndex < this.manifest.getPeriodCount() - 1;
                chunkHolder.endOfStream = z;
                return;
            }
            int i;
            int constrainValue;
            int firstSegmentNum = representationHolder.getFirstSegmentNum();
            if (segmentCount == -1) {
                nowUnixTimeUs = (nowUnixTimeUs - (this.manifest.availabilityStartTime * 1000)) - (this.manifest.getPeriod(this.periodIndex).startMs * 1000);
                if (this.manifest.timeShiftBufferDepth != C3446C.TIME_UNSET) {
                    firstSegmentNum = Math.max(firstSegmentNum, representationHolder.getSegmentNum(nowUnixTimeUs - (this.manifest.timeShiftBufferDepth * 1000)));
                }
                int segmentNum = representationHolder.getSegmentNum(nowUnixTimeUs) - 1;
                i = firstSegmentNum;
                firstSegmentNum = segmentNum;
            } else {
                i = firstSegmentNum;
                firstSegmentNum = (firstSegmentNum + segmentCount) - 1;
            }
            if (mediaChunk == null) {
                constrainValue = Util.constrainValue(representationHolder.getSegmentNum(j), i, firstSegmentNum);
            } else {
                constrainValue = mediaChunk.getNextChunkIndex();
                if (constrainValue < i) {
                    this.fatalError = new BehindLiveWindowException();
                    return;
                }
            }
            if (constrainValue > firstSegmentNum || (this.missingLastSegment && constrainValue >= firstSegmentNum)) {
                z = !this.manifest.dynamic || this.periodIndex < this.manifest.getPeriodCount() - 1;
                chunkHolder.endOfStream = z;
                return;
            }
            chunkHolder.chunk = newMediaChunk(representationHolder, this.dataSource, this.trackType, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), constrainValue, Math.min(this.maxSegmentsPerLoad, (firstSegmentNum - constrainValue) + 1));
        }
    }

    public int getPreferredQueueSize(long j, List<? extends MediaChunk> list) {
        return (this.fatalError != null || this.trackSelection.length() < 2) ? list.size() : this.trackSelection.evaluateQueueSize(j, list);
    }

    public void maybeThrowError() {
        if (this.fatalError != null) {
            throw this.fatalError;
        }
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof InitializationChunk) {
            RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat)];
            if (representationHolder.segmentIndex == null) {
                SeekMap seekMap = representationHolder.extractorWrapper.getSeekMap();
                if (seekMap != null) {
                    representationHolder.segmentIndex = new DashWrappingSegmentIndex((ChunkIndex) seekMap);
                }
            }
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean z, Exception exception) {
        if (!z) {
            return false;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk) && (exception instanceof InvalidResponseCodeException) && ((InvalidResponseCodeException) exception).responseCode == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
            RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
            int segmentCount = representationHolder.getSegmentCount();
            if (!(segmentCount == -1 || segmentCount == 0 || ((MediaChunk) chunk).getNextChunkIndex() <= (representationHolder.getFirstSegmentNum() + segmentCount) - 1)) {
                this.missingLastSegment = true;
                return true;
            }
        }
        return ChunkedTrackBlacklistUtil.maybeBlacklistTrack(this.trackSelection, this.trackSelection.indexOf(chunk.trackFormat), exception);
    }

    public void updateManifest(DashManifest dashManifest, int i) {
        try {
            this.manifest = dashManifest;
            this.periodIndex = i;
            long periodDurationUs = this.manifest.getPeriodDurationUs(this.periodIndex);
            List representations = getRepresentations();
            for (int i2 = 0; i2 < this.representationHolders.length; i2++) {
                this.representationHolders[i2].updateRepresentation(periodDurationUs, (Representation) representations.get(this.trackSelection.getIndexInTrackGroup(i2)));
            }
        } catch (IOException e) {
            this.fatalError = e;
        }
    }
}
