package org.telegram.messenger.exoplayer2.source;

import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.MediaPeriod.Callback;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public final class ClippingMediaPeriod implements MediaPeriod, Callback {
    private Callback callback;
    private long endUs = C3446C.TIME_UNSET;
    public final MediaPeriod mediaPeriod;
    private boolean pendingInitialDiscontinuity;
    private ClippingSampleStream[] sampleStreams = new ClippingSampleStream[0];
    private long startUs = C3446C.TIME_UNSET;

    private static final class ClippingSampleStream implements SampleStream {
        private final long endUs;
        private final MediaPeriod mediaPeriod;
        private boolean pendingDiscontinuity;
        private boolean sentEos;
        private final long startUs;
        private final SampleStream stream;

        public ClippingSampleStream(MediaPeriod mediaPeriod, SampleStream sampleStream, long j, long j2, boolean z) {
            this.mediaPeriod = mediaPeriod;
            this.stream = sampleStream;
            this.startUs = j;
            this.endUs = j2;
            this.pendingDiscontinuity = z;
        }

        public void clearPendingDiscontinuity() {
            this.pendingDiscontinuity = false;
        }

        public void clearSentEos() {
            this.sentEos = false;
        }

        public boolean isReady() {
            return this.stream.isReady();
        }

        public void maybeThrowError() {
            this.stream.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            if (this.pendingDiscontinuity) {
                return -3;
            }
            if (this.sentEos) {
                decoderInputBuffer.setFlags(4);
                return -4;
            }
            int readData = this.stream.readData(formatHolder, decoderInputBuffer, z);
            if (this.endUs == Long.MIN_VALUE || ((readData != -4 || decoderInputBuffer.timeUs < this.endUs) && !(readData == -3 && this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE))) {
                if (readData == -4 && !decoderInputBuffer.isEndOfStream()) {
                    decoderInputBuffer.timeUs -= this.startUs;
                }
                return readData;
            }
            decoderInputBuffer.clear();
            decoderInputBuffer.setFlags(4);
            this.sentEos = true;
            return -4;
        }

        public void skipData(long j) {
            this.stream.skipData(this.startUs + j);
        }
    }

    public ClippingMediaPeriod(MediaPeriod mediaPeriod, boolean z) {
        this.mediaPeriod = mediaPeriod;
        this.pendingInitialDiscontinuity = z;
    }

    private static boolean shouldKeepInitialDiscontinuity(TrackSelection[] trackSelectionArr) {
        for (TrackSelection trackSelection : trackSelectionArr) {
            if (trackSelection != null && !MimeTypes.isAudio(trackSelection.getSelectedFormat().sampleMimeType)) {
                return true;
            }
        }
        return false;
    }

    public boolean continueLoading(long j) {
        return this.mediaPeriod.continueLoading(this.startUs + j);
    }

    public void discardBuffer(long j) {
        this.mediaPeriod.discardBuffer(this.startUs + j);
    }

    public long getBufferedPositionUs() {
        long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
        return bufferedPositionUs != Long.MIN_VALUE ? (this.endUs == Long.MIN_VALUE || bufferedPositionUs < this.endUs) ? Math.max(0, bufferedPositionUs - this.startUs) : Long.MIN_VALUE : Long.MIN_VALUE;
    }

    public long getNextLoadPositionUs() {
        long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
        return nextLoadPositionUs != Long.MIN_VALUE ? (this.endUs == Long.MIN_VALUE || nextLoadPositionUs < this.endUs) ? nextLoadPositionUs - this.startUs : Long.MIN_VALUE : Long.MIN_VALUE;
    }

    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    public void maybeThrowPrepareError() {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.callback.onContinueLoadingRequested(this);
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        boolean z = (this.startUs == C3446C.TIME_UNSET || this.endUs == C3446C.TIME_UNSET) ? false : true;
        Assertions.checkState(z);
        this.callback.onPrepared(this);
    }

    public void prepare(Callback callback, long j) {
        this.callback = callback;
        this.mediaPeriod.prepare(this, this.startUs + j);
    }

    public long readDiscontinuity() {
        boolean z = false;
        if (this.pendingInitialDiscontinuity) {
            for (ClippingSampleStream clippingSampleStream : this.sampleStreams) {
                if (clippingSampleStream != null) {
                    clippingSampleStream.clearPendingDiscontinuity();
                }
            }
            this.pendingInitialDiscontinuity = false;
            long readDiscontinuity = readDiscontinuity();
            return readDiscontinuity != C3446C.TIME_UNSET ? readDiscontinuity : 0;
        } else {
            long readDiscontinuity2 = this.mediaPeriod.readDiscontinuity();
            if (readDiscontinuity2 == C3446C.TIME_UNSET) {
                return C3446C.TIME_UNSET;
            }
            Assertions.checkState(readDiscontinuity2 >= this.startUs);
            if (this.endUs == Long.MIN_VALUE || readDiscontinuity2 <= this.endUs) {
                z = true;
            }
            Assertions.checkState(z);
            return readDiscontinuity2 - this.startUs;
        }
    }

    public long seekToUs(long j) {
        boolean z = false;
        for (ClippingSampleStream clippingSampleStream : this.sampleStreams) {
            if (clippingSampleStream != null) {
                clippingSampleStream.clearSentEos();
            }
        }
        long seekToUs = this.mediaPeriod.seekToUs(this.startUs + j);
        if (seekToUs == this.startUs + j || (seekToUs >= this.startUs && (this.endUs == Long.MIN_VALUE || seekToUs <= this.endUs))) {
            z = true;
        }
        Assertions.checkState(z);
        return seekToUs - this.startUs;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        boolean z;
        this.sampleStreams = new ClippingSampleStream[sampleStreamArr.length];
        SampleStream[] sampleStreamArr2 = new SampleStream[sampleStreamArr.length];
        for (int i = 0; i < sampleStreamArr.length; i++) {
            this.sampleStreams[i] = (ClippingSampleStream) sampleStreamArr[i];
            sampleStreamArr2[i] = this.sampleStreams[i] != null ? this.sampleStreams[i].stream : null;
        }
        long selectTracks = this.mediaPeriod.selectTracks(trackSelectionArr, zArr, sampleStreamArr2, zArr2, j + this.startUs);
        if (this.pendingInitialDiscontinuity) {
            z = this.startUs != 0 && shouldKeepInitialDiscontinuity(trackSelectionArr);
            this.pendingInitialDiscontinuity = z;
        }
        z = selectTracks == this.startUs + j || (selectTracks >= this.startUs && (this.endUs == Long.MIN_VALUE || selectTracks <= this.endUs));
        Assertions.checkState(z);
        int i2 = 0;
        while (i2 < sampleStreamArr.length) {
            if (sampleStreamArr2[i2] == null) {
                this.sampleStreams[i2] = null;
            } else if (sampleStreamArr[i2] == null || this.sampleStreams[i2].stream != sampleStreamArr2[i2]) {
                this.sampleStreams[i2] = new ClippingSampleStream(this, sampleStreamArr2[i2], this.startUs, this.endUs, this.pendingInitialDiscontinuity);
            }
            sampleStreamArr[i2] = this.sampleStreams[i2];
            i2++;
        }
        return selectTracks - this.startUs;
    }

    public void setClipping(long j, long j2) {
        this.startUs = j;
        this.endUs = j2;
    }
}
