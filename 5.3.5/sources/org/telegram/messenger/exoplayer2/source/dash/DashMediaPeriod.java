package org.telegram.messenger.exoplayer2.source.dash;

import android.util.Pair;
import android.util.SparseIntArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
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

        public TrackGroupInfo(int trackType, int[] adaptationSetIndices, int primaryTrackGroupIndex, boolean isPrimary, boolean hasEmbeddedEventMessageTrack, boolean hasEmbeddedCea608Track) {
            this.trackType = trackType;
            this.adaptationSetIndices = adaptationSetIndices;
            this.primaryTrackGroupIndex = primaryTrackGroupIndex;
            this.isPrimary = isPrimary;
            this.hasEmbeddedEventMessageTrack = hasEmbeddedEventMessageTrack;
            this.hasEmbeddedCea608Track = hasEmbeddedCea608Track;
        }
    }

    public DashMediaPeriod(int id, DashManifest manifest, int periodIndex, Factory chunkSourceFactory, int minLoadableRetryCount, EventDispatcher eventDispatcher, long elapsedRealtimeOffset, LoaderErrorThrower manifestLoaderErrorThrower, Allocator allocator) {
        this.id = id;
        this.manifest = manifest;
        this.periodIndex = periodIndex;
        this.chunkSourceFactory = chunkSourceFactory;
        this.minLoadableRetryCount = minLoadableRetryCount;
        this.eventDispatcher = eventDispatcher;
        this.elapsedRealtimeOffset = elapsedRealtimeOffset;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower;
        this.allocator = allocator;
        this.adaptationSets = manifest.getPeriod(periodIndex).adaptationSets;
        Pair<TrackGroupArray, TrackGroupInfo[]> result = buildTrackGroups(this.adaptationSets);
        this.trackGroups = (TrackGroupArray) result.first;
        this.trackGroupInfos = (TrackGroupInfo[]) result.second;
    }

    public void updateManifest(DashManifest manifest, int periodIndex) {
        this.manifest = manifest;
        this.periodIndex = periodIndex;
        this.adaptationSets = manifest.getPeriod(periodIndex).adaptationSets;
        if (this.sampleStreams != null) {
            for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
                ((DashChunkSource) sampleStream.getChunkSource()).updateManifest(manifest, periodIndex);
            }
            this.callback.onContinueLoadingRequested(this);
        }
    }

    public void release() {
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.release();
        }
    }

    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        this.callback = callback;
        callback.onPrepared(this);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        TrackGroupInfo trackGroupInfo;
        HashMap<Integer, ChunkSampleStream<DashChunkSource>> primarySampleStreams = new HashMap();
        int i = 0;
        while (i < selections.length) {
            ChunkSampleStream<DashChunkSource> stream;
            if (streams[i] instanceof ChunkSampleStream) {
                stream = streams[i];
                if (selections[i] == null || !mayRetainStreamFlags[i]) {
                    stream.release();
                    streams[i] = null;
                } else {
                    primarySampleStreams.put(Integer.valueOf(this.trackGroups.indexOf(selections[i].getTrackGroup())), stream);
                }
            }
            if (streams[i] == null && selections[i] != null) {
                int trackGroupIndex = this.trackGroups.indexOf(selections[i].getTrackGroup());
                trackGroupInfo = this.trackGroupInfos[trackGroupIndex];
                if (trackGroupInfo.isPrimary) {
                    stream = buildSampleStream(trackGroupInfo, selections[i], positionUs);
                    primarySampleStreams.put(Integer.valueOf(trackGroupIndex), stream);
                    streams[i] = stream;
                    streamResetFlags[i] = true;
                }
            }
            i++;
        }
        i = 0;
        while (i < selections.length) {
            if (((streams[i] instanceof EmbeddedSampleStream) || (streams[i] instanceof EmptySampleStream)) && (selections[i] == null || !mayRetainStreamFlags[i])) {
                releaseIfEmbeddedSampleStream(streams[i]);
                streams[i] = null;
            }
            if (selections[i] != null) {
                trackGroupInfo = this.trackGroupInfos[this.trackGroups.indexOf(selections[i].getTrackGroup())];
                if (!trackGroupInfo.isPrimary) {
                    ChunkSampleStream<?> primaryStream = (ChunkSampleStream) primarySampleStreams.get(Integer.valueOf(trackGroupInfo.primaryTrackGroupIndex));
                    SampleStream stream2 = streams[i];
                    boolean mayRetainStream = primaryStream == null ? stream2 instanceof EmptySampleStream : (stream2 instanceof EmbeddedSampleStream) && ((EmbeddedSampleStream) stream2).parent == primaryStream;
                    if (!mayRetainStream) {
                        EmptySampleStream emptySampleStream;
                        releaseIfEmbeddedSampleStream(stream2);
                        if (primaryStream == null) {
                            emptySampleStream = new EmptySampleStream();
                        } else {
                            emptySampleStream = primaryStream.selectEmbeddedTrack(positionUs, trackGroupInfo.trackType);
                        }
                        streams[i] = emptySampleStream;
                        streamResetFlags[i] = true;
                    }
                }
            }
            i++;
        }
        this.sampleStreams = newSampleStreamArray(primarySampleStreams.size());
        primarySampleStreams.values().toArray(this.sampleStreams);
        this.sequenceableLoader = new CompositeSequenceableLoader(this.sampleStreams);
        return positionUs;
    }

    public void discardBuffer(long positionUs) {
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.discardEmbeddedTracksTo(positionUs);
        }
    }

    public boolean continueLoading(long positionUs) {
        return this.sequenceableLoader.continueLoading(positionUs);
    }

    public long getNextLoadPositionUs() {
        return this.sequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        return C0907C.TIME_UNSET;
    }

    public long getBufferedPositionUs() {
        return this.sequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long positionUs) {
        for (ChunkSampleStream<DashChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.seekToUs(positionUs);
        }
        return positionUs;
    }

    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> buildTrackGroups(List<AdaptationSet> adaptationSets) {
        int i;
        int[][] groupedAdaptationSetIndices = getGroupedAdaptationSetIndices(adaptationSets);
        int primaryGroupCount = groupedAdaptationSetIndices.length;
        boolean[] primaryGroupHasEventMessageTrackFlags = new boolean[primaryGroupCount];
        boolean[] primaryGroupHasCea608TrackFlags = new boolean[primaryGroupCount];
        int totalGroupCount = primaryGroupCount;
        for (i = 0; i < primaryGroupCount; i++) {
            if (hasEventMessageTrack(adaptationSets, groupedAdaptationSetIndices[i])) {
                primaryGroupHasEventMessageTrackFlags[i] = true;
                totalGroupCount++;
            }
            if (hasCea608Track(adaptationSets, groupedAdaptationSetIndices[i])) {
                primaryGroupHasCea608TrackFlags[i] = true;
                totalGroupCount++;
            }
        }
        TrackGroup[] trackGroups = new TrackGroup[totalGroupCount];
        Object trackGroupInfos = new TrackGroupInfo[totalGroupCount];
        i = 0;
        int trackGroupCount = 0;
        while (i < primaryGroupCount) {
            int[] adaptationSetIndices = groupedAdaptationSetIndices[i];
            List<Representation> representations = new ArrayList();
            for (int adaptationSetIndex : adaptationSetIndices) {
                representations.addAll(((AdaptationSet) adaptationSets.get(adaptationSetIndex)).representations);
            }
            Format[] formats = new Format[representations.size()];
            for (int j = 0; j < formats.length; j++) {
                formats[j] = ((Representation) representations.get(j)).format;
            }
            AdaptationSet firstAdaptationSet = (AdaptationSet) adaptationSets.get(adaptationSetIndices[0]);
            int primaryTrackGroupIndex = trackGroupCount;
            boolean hasEventMessageTrack = primaryGroupHasEventMessageTrackFlags[i];
            boolean hasCea608Track = primaryGroupHasCea608TrackFlags[i];
            trackGroups[trackGroupCount] = new TrackGroup(formats);
            int trackGroupCount2 = trackGroupCount + 1;
            trackGroupInfos[trackGroupCount] = new TrackGroupInfo(firstAdaptationSet.type, adaptationSetIndices, primaryTrackGroupIndex, true, hasEventMessageTrack, hasCea608Track);
            if (hasEventMessageTrack) {
                Format format = Format.createSampleFormat(firstAdaptationSet.id + ":emsg", MimeTypes.APPLICATION_EMSG, null, -1, null);
                trackGroups[trackGroupCount2] = new TrackGroup(format);
                trackGroupCount = trackGroupCount2 + 1;
                trackGroupInfos[trackGroupCount2] = new TrackGroupInfo(4, adaptationSetIndices, primaryTrackGroupIndex, false, false, false);
            } else {
                trackGroupCount = trackGroupCount2;
            }
            if (hasCea608Track) {
                format = Format.createTextSampleFormat(firstAdaptationSet.id + ":cea608", MimeTypes.APPLICATION_CEA608, 0, null);
                trackGroups[trackGroupCount] = new TrackGroup(format);
                trackGroupCount2 = trackGroupCount + 1;
                trackGroupInfos[trackGroupCount] = new TrackGroupInfo(3, adaptationSetIndices, primaryTrackGroupIndex, false, false, false);
            } else {
                trackGroupCount2 = trackGroupCount;
            }
            i++;
            trackGroupCount = trackGroupCount2;
        }
        return Pair.create(new TrackGroupArray(trackGroups), trackGroupInfos);
    }

    private static int[][] getGroupedAdaptationSetIndices(List<AdaptationSet> adaptationSets) {
        int i;
        int adaptationSetCount = adaptationSets.size();
        SparseIntArray idToIndexMap = new SparseIntArray(adaptationSetCount);
        for (i = 0; i < adaptationSetCount; i++) {
            idToIndexMap.put(((AdaptationSet) adaptationSets.get(i)).id, i);
        }
        int[][] groupedAdaptationSetIndices = new int[adaptationSetCount][];
        boolean[] adaptationSetUsedFlags = new boolean[adaptationSetCount];
        i = 0;
        int groupCount = 0;
        while (i < adaptationSetCount) {
            int groupCount2;
            if (adaptationSetUsedFlags[i]) {
                groupCount2 = groupCount;
            } else {
                adaptationSetUsedFlags[i] = true;
                Descriptor adaptationSetSwitchingProperty = findAdaptationSetSwitchingProperty(((AdaptationSet) adaptationSets.get(i)).supplementalProperties);
                if (adaptationSetSwitchingProperty == null) {
                    groupCount2 = groupCount + 1;
                    groupedAdaptationSetIndices[groupCount] = new int[]{i};
                } else {
                    String[] extraAdaptationSetIds = adaptationSetSwitchingProperty.value.split(",");
                    int[] adaptationSetIndices = new int[(extraAdaptationSetIds.length + 1)];
                    adaptationSetIndices[0] = i;
                    for (int j = 0; j < extraAdaptationSetIds.length; j++) {
                        int extraIndex = idToIndexMap.get(Integer.parseInt(extraAdaptationSetIds[j]));
                        adaptationSetUsedFlags[extraIndex] = true;
                        adaptationSetIndices[j + 1] = extraIndex;
                    }
                    groupCount2 = groupCount + 1;
                    groupedAdaptationSetIndices[groupCount] = adaptationSetIndices;
                }
            }
            i++;
            groupCount = groupCount2;
        }
        return groupCount < adaptationSetCount ? (int[][]) Arrays.copyOf(groupedAdaptationSetIndices, groupCount) : groupedAdaptationSetIndices;
    }

    private ChunkSampleStream<DashChunkSource> buildSampleStream(TrackGroupInfo trackGroupInfo, TrackSelection selection, long positionUs) {
        int i = 0;
        int[] embeddedTrackTypes = new int[2];
        boolean enableEventMessageTrack = trackGroupInfo.hasEmbeddedEventMessageTrack;
        if (enableEventMessageTrack) {
            int embeddedTrackCount = 0 + 1;
            embeddedTrackTypes[0] = 4;
            i = embeddedTrackCount;
        }
        boolean enableCea608Track = trackGroupInfo.hasEmbeddedCea608Track;
        if (enableCea608Track) {
            embeddedTrackCount = i + 1;
            embeddedTrackTypes[i] = 3;
            i = embeddedTrackCount;
        }
        if (i < embeddedTrackTypes.length) {
            embeddedTrackTypes = Arrays.copyOf(embeddedTrackTypes, i);
        }
        return new ChunkSampleStream(trackGroupInfo.trackType, embeddedTrackTypes, this.chunkSourceFactory.createDashChunkSource(this.manifestLoaderErrorThrower, this.manifest, this.periodIndex, trackGroupInfo.adaptationSetIndices, selection, trackGroupInfo.trackType, this.elapsedRealtimeOffset, enableEventMessageTrack, enableCea608Track), this, this.allocator, positionUs, this.minLoadableRetryCount, this.eventDispatcher);
    }

    private static Descriptor findAdaptationSetSwitchingProperty(List<Descriptor> descriptors) {
        for (int i = 0; i < descriptors.size(); i++) {
            Descriptor descriptor = (Descriptor) descriptors.get(i);
            if ("urn:mpeg:dash:adaptation-set-switching:2016".equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    private static boolean hasEventMessageTrack(List<AdaptationSet> adaptationSets, int[] adaptationSetIndices) {
        for (int i : adaptationSetIndices) {
            List<Representation> representations = ((AdaptationSet) adaptationSets.get(i)).representations;
            for (int j = 0; j < representations.size(); j++) {
                if (!((Representation) representations.get(j)).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasCea608Track(List<AdaptationSet> adaptationSets, int[] adaptationSetIndices) {
        for (int i : adaptationSetIndices) {
            List<Descriptor> descriptors = ((AdaptationSet) adaptationSets.get(i)).accessibilityDescriptors;
            for (int j = 0; j < descriptors.size(); j++) {
                if ("urn:scte:dash:cc:cea-608:2015".equals(((Descriptor) descriptors.get(j)).schemeIdUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ChunkSampleStream<DashChunkSource>[] newSampleStreamArray(int length) {
        return new ChunkSampleStream[length];
    }

    private static void releaseIfEmbeddedSampleStream(SampleStream sampleStream) {
        if (sampleStream instanceof EmbeddedSampleStream) {
            ((EmbeddedSampleStream) sampleStream).release();
        }
    }
}
