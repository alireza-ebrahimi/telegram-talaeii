package org.telegram.messenger.exoplayer2.source.dash;

import android.util.Pair;
import android.util.SparseIntArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.CompositeSequenceableLoader;
import org.telegram.messenger.exoplayer2.source.EmptySampleStream;
import org.telegram.messenger.exoplayer2.source.MediaPeriod;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.source.SequenceableLoader.Callback;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkSampleStream;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkSampleStream.EmbeddedSampleStream;
import org.telegram.messenger.exoplayer2.source.dash.DashChunkSource.Factory;
import org.telegram.messenger.exoplayer2.source.dash.manifest.AdaptationSet;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifest;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Descriptor;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Representation;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

final class DashMediaPeriod implements MediaPeriod, Callback<ChunkSampleStream<DashChunkSource>> {
    private List<AdaptationSet> adaptationSets;
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final Factory chunkSourceFactory;
    private final long elapsedRealtimeOffset;
    private final EventDispatcher eventDispatcher;
    final int id;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int minLoadableRetryCount;
    private int periodIndex;
    private ChunkSampleStream<DashChunkSource>[] sampleStreams = newSampleStreamArray(0);
    private CompositeSequenceableLoader sequenceableLoader = new CompositeSequenceableLoader(this.sampleStreams);
    private final TrackGroupInfo[] trackGroupInfos;
    private final TrackGroupArray trackGroups;

    private static final class TrackGroupInfo {
        public final int[] adaptationSetIndices;
        public final boolean hasEmbeddedCea608Track;
        public final boolean hasEmbeddedEventMessageTrack;
        public final boolean isPrimary;
        public final int primaryTrackGroupIndex;
        public final int trackType;

        public TrackGroupInfo(int i, int[] iArr, int i2, boolean z, boolean z2, boolean z3) {
            this.trackType = i;
            this.adaptationSetIndices = iArr;
            this.primaryTrackGroupIndex = i2;
            this.isPrimary = z;
            this.hasEmbeddedEventMessageTrack = z2;
            this.hasEmbeddedCea608Track = z3;
        }
    }

    public DashMediaPeriod(int i, DashManifest dashManifest, int i2, Factory factory, int i3, EventDispatcher eventDispatcher, long j, LoaderErrorThrower loaderErrorThrower, Allocator allocator) {
        this.id = i;
        this.manifest = dashManifest;
        this.periodIndex = i2;
        this.chunkSourceFactory = factory;
        this.minLoadableRetryCount = i3;
        this.eventDispatcher = eventDispatcher;
        this.elapsedRealtimeOffset = j;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.allocator = allocator;
        this.adaptationSets = dashManifest.getPeriod(i2).adaptationSets;
        Pair buildTrackGroups = buildTrackGroups(this.adaptationSets);
        this.trackGroups = (TrackGroupArray) buildTrackGroups.first;
        this.trackGroupInfos = (TrackGroupInfo[]) buildTrackGroups.second;
    }

    private ChunkSampleStream<DashChunkSource> buildSampleStream(TrackGroupInfo trackGroupInfo, TrackSelection trackSelection, long j) {
        int i;
        int[] iArr = new int[2];
        boolean z = trackGroupInfo.hasEmbeddedEventMessageTrack;
        if (z) {
            i = 1;
            iArr[0] = 4;
        } else {
            i = 0;
        }
        boolean z2 = trackGroupInfo.hasEmbeddedCea608Track;
        if (z2) {
            int i2 = i + 1;
            iArr[i] = 3;
            i = i2;
        }
        return new ChunkSampleStream(trackGroupInfo.trackType, i < iArr.length ? Arrays.copyOf(iArr, i) : iArr, this.chunkSourceFactory.createDashChunkSource(this.manifestLoaderErrorThrower, this.manifest, this.periodIndex, trackGroupInfo.adaptationSetIndices, trackSelection, trackGroupInfo.trackType, this.elapsedRealtimeOffset, z, z2), this, this.allocator, j, this.minLoadableRetryCount, this.eventDispatcher);
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> buildTrackGroups(List<AdaptationSet> list) {
        int i;
        int[][] groupedAdaptationSetIndices = getGroupedAdaptationSetIndices(list);
        int length = groupedAdaptationSetIndices.length;
        boolean[] zArr = new boolean[length];
        boolean[] zArr2 = new boolean[length];
        int i2 = length;
        for (i = 0; i < length; i++) {
            if (hasEventMessageTrack(list, groupedAdaptationSetIndices[i])) {
                zArr[i] = true;
                i2++;
            }
            if (hasCea608Track(list, groupedAdaptationSetIndices[i])) {
                zArr2[i] = true;
                i2++;
            }
        }
        TrackGroup[] trackGroupArr = new TrackGroup[i2];
        Object obj = new TrackGroupInfo[i2];
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            int i5;
            int[] iArr = groupedAdaptationSetIndices[i4];
            List arrayList = new ArrayList();
            for (int i22 : iArr) {
                arrayList.addAll(((AdaptationSet) list.get(i22)).representations);
            }
            Format[] formatArr = new Format[arrayList.size()];
            for (i = 0; i < formatArr.length; i++) {
                formatArr[i] = ((Representation) arrayList.get(i)).format;
            }
            AdaptationSet adaptationSet = (AdaptationSet) list.get(iArr[0]);
            boolean z = zArr[i4];
            boolean z2 = zArr2[i4];
            trackGroupArr[i3] = new TrackGroup(formatArr);
            int i6 = i3 + 1;
            obj[i3] = new TrackGroupInfo(adaptationSet.type, iArr, i3, true, z, z2);
            if (z) {
                trackGroupArr[i6] = new TrackGroup(Format.createSampleFormat(adaptationSet.id + ":emsg", MimeTypes.APPLICATION_EMSG, null, -1, null));
                i22 = i6 + 1;
                obj[i6] = new TrackGroupInfo(4, iArr, i3, false, false, false);
                i5 = i22;
            } else {
                i5 = i6;
            }
            if (z2) {
                trackGroupArr[i5] = new TrackGroup(Format.createTextSampleFormat(adaptationSet.id + ":cea608", MimeTypes.APPLICATION_CEA608, 0, null));
                int i7 = i5 + 1;
                obj[i5] = new TrackGroupInfo(3, iArr, i3, false, false, false);
                i22 = i7;
            } else {
                i22 = i5;
            }
            i4++;
            i3 = i22;
        }
        return Pair.create(new TrackGroupArray(trackGroupArr), obj);
    }

    private static Descriptor findAdaptationSetSwitchingProperty(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:mpeg:dash:adaptation-set-switching:2016".equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    private static int[][] getGroupedAdaptationSetIndices(List<AdaptationSet> list) {
        int i;
        int size = list.size();
        SparseIntArray sparseIntArray = new SparseIntArray(size);
        for (i = 0; i < size; i++) {
            sparseIntArray.put(((AdaptationSet) list.get(i)).id, i);
        }
        Object[] objArr = new int[size][];
        boolean[] zArr = new boolean[size];
        int i2 = 0;
        i = 0;
        while (i2 < size) {
            int i3;
            if (zArr[i2]) {
                i3 = i;
            } else {
                zArr[i2] = true;
                Descriptor findAdaptationSetSwitchingProperty = findAdaptationSetSwitchingProperty(((AdaptationSet) list.get(i2)).supplementalProperties);
                if (findAdaptationSetSwitchingProperty == null) {
                    i3 = i + 1;
                    objArr[i] = new int[]{i2};
                } else {
                    String[] split = findAdaptationSetSwitchingProperty.value.split(",");
                    int[] iArr = new int[(split.length + 1)];
                    iArr[0] = i2;
                    for (i3 = 0; i3 < split.length; i3++) {
                        int i4 = sparseIntArray.get(Integer.parseInt(split[i3]));
                        zArr[i4] = true;
                        iArr[i3 + 1] = i4;
                    }
                    i3 = i + 1;
                    objArr[i] = iArr;
                }
            }
            i2++;
            i = i3;
        }
        return i < size ? (int[][]) Arrays.copyOf(objArr, i) : objArr;
    }

    private static boolean hasCea608Track(List<AdaptationSet> list, int[] iArr) {
        for (int i : iArr) {
            List list2 = ((AdaptationSet) list.get(i)).accessibilityDescriptors;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if ("urn:scte:dash:cc:cea-608:2015".equals(((Descriptor) list2.get(i2)).schemeIdUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasEventMessageTrack(List<AdaptationSet> list, int[] iArr) {
        for (int i : iArr) {
            List list2 = ((AdaptationSet) list.get(i)).representations;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if (!((Representation) list2.get(i2)).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ChunkSampleStream<DashChunkSource>[] newSampleStreamArray(int i) {
        return new ChunkSampleStream[i];
    }

    private static void releaseIfEmbeddedSampleStream(SampleStream sampleStream) {
        if (sampleStream instanceof EmbeddedSampleStream) {
            ((EmbeddedSampleStream) sampleStream).release();
        }
    }

    public boolean continueLoading(long j) {
        return this.sequenceableLoader.continueLoading(j);
    }

    public void discardBuffer(long j) {
        for (ChunkSampleStream discardEmbeddedTracksTo : this.sampleStreams) {
            discardEmbeddedTracksTo.discardEmbeddedTracksTo(j);
        }
    }

    public long getBufferedPositionUs() {
        return this.sequenceableLoader.getBufferedPositionUs();
    }

    public long getNextLoadPositionUs() {
        return this.sequenceableLoader.getNextLoadPositionUs();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public void maybeThrowPrepareError() {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        callback.onPrepared(this);
    }

    public long readDiscontinuity() {
        return C3446C.TIME_UNSET;
    }

    public void release() {
        for (ChunkSampleStream release : this.sampleStreams) {
            release.release();
        }
    }

    public long seekToUs(long j) {
        for (ChunkSampleStream seekToUs : this.sampleStreams) {
            seekToUs.seekToUs(j);
        }
        return j;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i < trackSelectionArr.length) {
            ChunkSampleStream chunkSampleStream;
            if (sampleStreamArr[i] instanceof ChunkSampleStream) {
                chunkSampleStream = (ChunkSampleStream) sampleStreamArr[i];
                if (trackSelectionArr[i] == null || !zArr[i]) {
                    chunkSampleStream.release();
                    sampleStreamArr[i] = null;
                } else {
                    hashMap.put(Integer.valueOf(this.trackGroups.indexOf(trackSelectionArr[i].getTrackGroup())), chunkSampleStream);
                }
            }
            if (sampleStreamArr[i] == null && trackSelectionArr[i] != null) {
                int indexOf = this.trackGroups.indexOf(trackSelectionArr[i].getTrackGroup());
                TrackGroupInfo trackGroupInfo = this.trackGroupInfos[indexOf];
                if (trackGroupInfo.isPrimary) {
                    ChunkSampleStream buildSampleStream = buildSampleStream(trackGroupInfo, trackSelectionArr[i], j);
                    hashMap.put(Integer.valueOf(indexOf), buildSampleStream);
                    sampleStreamArr[i] = buildSampleStream;
                    zArr2[i] = true;
                }
            }
            i++;
        }
        int i2 = 0;
        while (i2 < trackSelectionArr.length) {
            if (((sampleStreamArr[i2] instanceof EmbeddedSampleStream) || (sampleStreamArr[i2] instanceof EmptySampleStream)) && (trackSelectionArr[i2] == null || !zArr[i2])) {
                releaseIfEmbeddedSampleStream(sampleStreamArr[i2]);
                sampleStreamArr[i2] = null;
            }
            if (trackSelectionArr[i2] != null) {
                TrackGroupInfo trackGroupInfo2 = this.trackGroupInfos[this.trackGroups.indexOf(trackSelectionArr[i2].getTrackGroup())];
                if (!trackGroupInfo2.isPrimary) {
                    chunkSampleStream = (ChunkSampleStream) hashMap.get(Integer.valueOf(trackGroupInfo2.primaryTrackGroupIndex));
                    SampleStream sampleStream = sampleStreamArr[i2];
                    boolean z = chunkSampleStream == null ? sampleStream instanceof EmptySampleStream : (sampleStream instanceof EmbeddedSampleStream) && ((EmbeddedSampleStream) sampleStream).parent == chunkSampleStream;
                    if (!z) {
                        releaseIfEmbeddedSampleStream(sampleStream);
                        sampleStreamArr[i2] = chunkSampleStream == null ? new EmptySampleStream() : chunkSampleStream.selectEmbeddedTrack(j, trackGroupInfo2.trackType);
                        zArr2[i2] = true;
                    }
                }
            }
            i2++;
        }
        this.sampleStreams = newSampleStreamArray(hashMap.size());
        hashMap.values().toArray(this.sampleStreams);
        this.sequenceableLoader = new CompositeSequenceableLoader(this.sampleStreams);
        return j;
    }

    public void updateManifest(DashManifest dashManifest, int i) {
        this.manifest = dashManifest;
        this.periodIndex = i;
        this.adaptationSets = dashManifest.getPeriod(i).adaptationSets;
        if (this.sampleStreams != null) {
            for (ChunkSampleStream chunkSource : this.sampleStreams) {
                ((DashChunkSource) chunkSource.getChunkSource()).updateManifest(dashManifest, i);
            }
            this.callback.onContinueLoadingRequested(this);
        }
    }
}
