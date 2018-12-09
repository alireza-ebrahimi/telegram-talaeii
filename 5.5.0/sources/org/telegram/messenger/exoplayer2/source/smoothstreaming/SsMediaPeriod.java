package org.telegram.messenger.exoplayer2.source.smoothstreaming;

import android.util.Base64;
import java.util.ArrayList;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.mp4.TrackEncryptionBox;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.CompositeSequenceableLoader;
import org.telegram.messenger.exoplayer2.source.MediaPeriod;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.source.SequenceableLoader.Callback;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkSampleStream;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.SsChunkSource.Factory;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;

final class SsMediaPeriod implements MediaPeriod, Callback<ChunkSampleStream<SsChunkSource>> {
    private static final int INITIALIZATION_VECTOR_SIZE = 8;
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final Factory chunkSourceFactory;
    private final EventDispatcher eventDispatcher;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int minLoadableRetryCount;
    private ChunkSampleStream<SsChunkSource>[] sampleStreams;
    private CompositeSequenceableLoader sequenceableLoader;
    private final TrackEncryptionBox[] trackEncryptionBoxes;
    private final TrackGroupArray trackGroups;

    public SsMediaPeriod(SsManifest ssManifest, Factory factory, int i, EventDispatcher eventDispatcher, LoaderErrorThrower loaderErrorThrower, Allocator allocator) {
        this.chunkSourceFactory = factory;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.minLoadableRetryCount = i;
        this.eventDispatcher = eventDispatcher;
        this.allocator = allocator;
        this.trackGroups = buildTrackGroups(ssManifest);
        if (ssManifest.protectionElement != null) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[]{new TrackEncryptionBox(true, null, 8, getProtectionElementKeyId(ssManifest.protectionElement.data), 0, 0, null)};
        } else {
            this.trackEncryptionBoxes = null;
        }
        this.manifest = ssManifest;
        this.sampleStreams = newSampleStreamArray(0);
        this.sequenceableLoader = new CompositeSequenceableLoader(this.sampleStreams);
    }

    private ChunkSampleStream<SsChunkSource> buildSampleStream(TrackSelection trackSelection, long j) {
        int indexOf = this.trackGroups.indexOf(trackSelection.getTrackGroup());
        return new ChunkSampleStream(this.manifest.streamElements[indexOf].type, null, this.chunkSourceFactory.createChunkSource(this.manifestLoaderErrorThrower, this.manifest, indexOf, trackSelection, this.trackEncryptionBoxes), this, this.allocator, j, this.minLoadableRetryCount, this.eventDispatcher);
    }

    private static TrackGroupArray buildTrackGroups(SsManifest ssManifest) {
        TrackGroup[] trackGroupArr = new TrackGroup[ssManifest.streamElements.length];
        for (int i = 0; i < ssManifest.streamElements.length; i++) {
            trackGroupArr[i] = new TrackGroup(ssManifest.streamElements[i].formats);
        }
        return new TrackGroupArray(trackGroupArr);
    }

    private static byte[] getProtectionElementKeyId(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bArr.length; i += 2) {
            stringBuilder.append((char) bArr[i]);
        }
        String stringBuilder2 = stringBuilder.toString();
        byte[] decode = Base64.decode(stringBuilder2.substring(stringBuilder2.indexOf("<KID>") + 5, stringBuilder2.indexOf("</KID>")), 0);
        swap(decode, 0, 3);
        swap(decode, 1, 2);
        swap(decode, 4, 5);
        swap(decode, 6, 7);
        return decode;
    }

    private static ChunkSampleStream<SsChunkSource>[] newSampleStreamArray(int i) {
        return new ChunkSampleStream[i];
    }

    private static void swap(byte[] bArr, int i, int i2) {
        byte b = bArr[i];
        bArr[i] = bArr[i2];
        bArr[i2] = b;
    }

    public boolean continueLoading(long j) {
        return this.sequenceableLoader.continueLoading(j);
    }

    public void discardBuffer(long j) {
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

    public void onContinueLoadingRequested(ChunkSampleStream<SsChunkSource> chunkSampleStream) {
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
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < trackSelectionArr.length) {
            ChunkSampleStream chunkSampleStream;
            if (sampleStreamArr[i] != null) {
                chunkSampleStream = (ChunkSampleStream) sampleStreamArr[i];
                if (trackSelectionArr[i] == null || !zArr[i]) {
                    chunkSampleStream.release();
                    sampleStreamArr[i] = null;
                } else {
                    arrayList.add(chunkSampleStream);
                }
            }
            if (sampleStreamArr[i] == null && trackSelectionArr[i] != null) {
                chunkSampleStream = buildSampleStream(trackSelectionArr[i], j);
                arrayList.add(chunkSampleStream);
                sampleStreamArr[i] = chunkSampleStream;
                zArr2[i] = true;
            }
            i++;
        }
        this.sampleStreams = newSampleStreamArray(arrayList.size());
        arrayList.toArray(this.sampleStreams);
        this.sequenceableLoader = new CompositeSequenceableLoader(this.sampleStreams);
        return j;
    }

    public void updateManifest(SsManifest ssManifest) {
        this.manifest = ssManifest;
        for (ChunkSampleStream chunkSource : this.sampleStreams) {
            ((SsChunkSource) chunkSource.getChunkSource()).updateManifest(ssManifest);
        }
        this.callback.onContinueLoadingRequested(this);
    }
}
