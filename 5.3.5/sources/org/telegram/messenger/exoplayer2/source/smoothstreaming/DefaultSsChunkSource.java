package org.telegram.messenger.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import java.io.IOException;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import org.telegram.messenger.exoplayer2.extractor.mp4.Track;
import org.telegram.messenger.exoplayer2.extractor.mp4.TrackEncryptionBox;
import org.telegram.messenger.exoplayer2.source.BehindLiveWindowException;
import org.telegram.messenger.exoplayer2.source.chunk.Chunk;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkExtractorWrapper;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkHolder;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;
import org.telegram.messenger.exoplayer2.source.chunk.ContainerMediaChunk;
import org.telegram.messenger.exoplayer2.source.chunk.MediaChunk;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.manifest.SsManifest.StreamElement;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;

public class DefaultSsChunkSource implements SsChunkSource {
    private int currentManifestChunkOffset;
    private final DataSource dataSource;
    private final int elementIndex;
    private final ChunkExtractorWrapper[] extractorWrappers;
    private IOException fatalError;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final TrackSelection trackSelection;

    public static final class Factory implements org.telegram.messenger.exoplayer2.source.smoothstreaming.SsChunkSource.Factory {
        private final org.telegram.messenger.exoplayer2.upstream.DataSource.Factory dataSourceFactory;

        public Factory(org.telegram.messenger.exoplayer2.upstream.DataSource.Factory dataSourceFactory) {
            this.dataSourceFactory = dataSourceFactory;
        }

        public SsChunkSource createChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, SsManifest manifest, int elementIndex, TrackSelection trackSelection, TrackEncryptionBox[] trackEncryptionBoxes) {
            return new DefaultSsChunkSource(manifestLoaderErrorThrower, manifest, elementIndex, trackSelection, this.dataSourceFactory.createDataSource(), trackEncryptionBoxes);
        }
    }

    public DefaultSsChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, SsManifest manifest, int elementIndex, TrackSelection trackSelection, DataSource dataSource, TrackEncryptionBox[] trackEncryptionBoxes) {
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower;
        this.manifest = manifest;
        this.elementIndex = elementIndex;
        this.trackSelection = trackSelection;
        this.dataSource = dataSource;
        StreamElement streamElement = manifest.streamElements[elementIndex];
        this.extractorWrappers = new ChunkExtractorWrapper[trackSelection.length()];
        for (int i = 0; i < this.extractorWrappers.length; i++) {
            int manifestTrackIndex = trackSelection.getIndexInTrackGroup(i);
            Format format = streamElement.formats[manifestTrackIndex];
            this.extractorWrappers[i] = new ChunkExtractorWrapper(new FragmentedMp4Extractor(3, null, new Track(manifestTrackIndex, streamElement.type, streamElement.timescale, C0907C.TIME_UNSET, manifest.durationUs, format, 0, trackEncryptionBoxes, streamElement.type == 2 ? 4 : 0, null, null)), format);
        }
    }

    public void updateManifest(SsManifest newManifest) {
        StreamElement currentElement = this.manifest.streamElements[this.elementIndex];
        int currentElementChunkCount = currentElement.chunkCount;
        StreamElement newElement = newManifest.streamElements[this.elementIndex];
        if (currentElementChunkCount == 0 || newElement.chunkCount == 0) {
            this.currentManifestChunkOffset += currentElementChunkCount;
        } else {
            long currentElementEndTimeUs = currentElement.getStartTimeUs(currentElementChunkCount - 1) + currentElement.getChunkDurationUs(currentElementChunkCount - 1);
            long newElementStartTimeUs = newElement.getStartTimeUs(0);
            if (currentElementEndTimeUs <= newElementStartTimeUs) {
                this.currentManifestChunkOffset += currentElementChunkCount;
            } else {
                this.currentManifestChunkOffset += currentElement.getChunkIndex(newElementStartTimeUs);
            }
        }
        this.manifest = newManifest;
    }

    public void maybeThrowError() throws IOException {
        if (this.fatalError != null) {
            throw this.fatalError;
        }
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public int getPreferredQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        if (this.fatalError != null || this.trackSelection.length() < 2) {
            return queue.size();
        }
        return this.trackSelection.evaluateQueueSize(playbackPositionUs, queue);
    }

    public final void getNextChunk(MediaChunk previous, long playbackPositionUs, ChunkHolder out) {
        if (this.fatalError == null) {
            this.trackSelection.updateSelectedTrack(previous != null ? previous.endTimeUs - playbackPositionUs : 0);
            StreamElement streamElement = this.manifest.streamElements[this.elementIndex];
            boolean z;
            if (streamElement.chunkCount == 0) {
                if (this.manifest.isLive) {
                    z = false;
                } else {
                    z = true;
                }
                out.endOfStream = z;
                return;
            }
            int chunkIndex;
            if (previous == null) {
                chunkIndex = streamElement.getChunkIndex(playbackPositionUs);
            } else {
                chunkIndex = previous.getNextChunkIndex() - this.currentManifestChunkOffset;
                if (chunkIndex < 0) {
                    this.fatalError = new BehindLiveWindowException();
                    return;
                }
            }
            if (chunkIndex >= streamElement.chunkCount) {
                if (this.manifest.isLive) {
                    z = false;
                } else {
                    z = true;
                }
                out.endOfStream = z;
                return;
            }
            long chunkStartTimeUs = streamElement.getStartTimeUs(chunkIndex);
            long chunkEndTimeUs = chunkStartTimeUs + streamElement.getChunkDurationUs(chunkIndex);
            int currentAbsoluteChunkIndex = chunkIndex + this.currentManifestChunkOffset;
            int trackSelectionIndex = this.trackSelection.getSelectedIndex();
            out.chunk = newMediaChunk(this.trackSelection.getSelectedFormat(), this.dataSource, streamElement.buildRequestUri(this.trackSelection.getIndexInTrackGroup(trackSelectionIndex), chunkIndex), null, currentAbsoluteChunkIndex, chunkStartTimeUs, chunkEndTimeUs, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.extractorWrappers[trackSelectionIndex]);
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
    }

    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, Exception e) {
        return cancelable && ChunkedTrackBlacklistUtil.maybeBlacklistTrack(this.trackSelection, this.trackSelection.indexOf(chunk.trackFormat), e);
    }

    private static MediaChunk newMediaChunk(Format format, DataSource dataSource, Uri uri, String cacheKey, int chunkIndex, long chunkStartTimeUs, long chunkEndTimeUs, int trackSelectionReason, Object trackSelectionData, ChunkExtractorWrapper extractorWrapper) {
        return new ContainerMediaChunk(dataSource, new DataSpec(uri, 0, -1, cacheKey), format, trackSelectionReason, trackSelectionData, chunkStartTimeUs, chunkEndTimeUs, chunkIndex, 1, chunkStartTimeUs, extractorWrapper);
    }
}
