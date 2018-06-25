package org.telegram.messenger.exoplayer2.source;

import java.io.IOException;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.MediaPeriod.Callback;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public final class ClippingMediaPeriod implements MediaPeriod, Callback {
    private Callback callback;
    private long endUs = C0907C.TIME_UNSET;
    public final MediaPeriod mediaPeriod;
    private boolean pendingInitialDiscontinuity;
    private ClippingSampleStream[] sampleStreams = new ClippingSampleStream[0];
    private long startUs = C0907C.TIME_UNSET;

    private static final class ClippingSampleStream implements SampleStream {
        private final long endUs;
        private final MediaPeriod mediaPeriod;
        private boolean pendingDiscontinuity;
        private boolean sentEos;
        private final long startUs;
        private final SampleStream stream;

        public ClippingSampleStream(MediaPeriod mediaPeriod, SampleStream stream, long startUs, long endUs, boolean pendingDiscontinuity) {
            this.mediaPeriod = mediaPeriod;
            this.stream = stream;
            this.startUs = startUs;
            this.endUs = endUs;
            this.pendingDiscontinuity = pendingDiscontinuity;
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

        public void maybeThrowError() throws IOException {
            this.stream.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean requireFormat) {
            if (this.pendingDiscontinuity) {
                return -3;
            }
            if (this.sentEos) {
                buffer.setFlags(4);
                return -4;
            }
            int result = this.stream.readData(formatHolder, buffer, requireFormat);
            if (this.endUs != Long.MIN_VALUE && ((result == -4 && buffer.timeUs >= this.endUs) || (result == -3 && this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE))) {
                buffer.clear();
                buffer.setFlags(4);
                this.sentEos = true;
                return -4;
            } else if (result != -4 || buffer.isEndOfStream()) {
                return result;
            } else {
                buffer.timeUs -= this.startUs;
                return result;
            }
        }

        public void skipData(long positionUs) {
            this.stream.skipData(this.startUs + positionUs);
        }
    }

    public ClippingMediaPeriod(MediaPeriod mediaPeriod, boolean enableInitialDiscontinuity) {
        this.mediaPeriod = mediaPeriod;
        this.pendingInitialDiscontinuity = enableInitialDiscontinuity;
    }

    public void setClipping(long startUs, long endUs) {
        this.startUs = startUs;
        this.endUs = endUs;
    }

    public void prepare(Callback callback, long positionUs) {
        this.callback = callback;
        this.mediaPeriod.prepare(this, this.startUs + positionUs);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int i;
        boolean z;
        this.sampleStreams = new ClippingSampleStream[streams.length];
        SampleStream[] internalStreams = new SampleStream[streams.length];
        for (i = 0; i < streams.length; i++) {
            this.sampleStreams[i] = (ClippingSampleStream) streams[i];
            internalStreams[i] = this.sampleStreams[i] != null ? this.sampleStreams[i].stream : null;
        }
        long enablePositionUs = this.mediaPeriod.selectTracks(selections, mayRetainStreamFlags, internalStreams, streamResetFlags, positionUs + this.startUs);
        if (this.pendingInitialDiscontinuity) {
            z = this.startUs != 0 && shouldKeepInitialDiscontinuity(selections);
            this.pendingInitialDiscontinuity = z;
        }
        z = enablePositionUs == this.startUs + positionUs || (enablePositionUs >= this.startUs && (this.endUs == Long.MIN_VALUE || enablePositionUs <= this.endUs));
        Assertions.checkState(z);
        i = 0;
        while (i < streams.length) {
            if (internalStreams[i] == null) {
                this.sampleStreams[i] = null;
            } else if (streams[i] == null || this.sampleStreams[i].stream != internalStreams[i]) {
                this.sampleStreams[i] = new ClippingSampleStream(this, internalStreams[i], this.startUs, this.endUs, this.pendingInitialDiscontinuity);
            }
            streams[i] = this.sampleStreams[i];
            i++;
        }
        return enablePositionUs - this.startUs;
    }

    public void discardBuffer(long positionUs) {
        this.mediaPeriod.discardBuffer(this.startUs + positionUs);
    }

    public long readDiscontinuity() {
        boolean z = false;
        long discontinuityUs;
        if (this.pendingInitialDiscontinuity) {
            for (ClippingSampleStream sampleStream : this.sampleStreams) {
                if (sampleStream != null) {
                    sampleStream.clearPendingDiscontinuity();
                }
            }
            this.pendingInitialDiscontinuity = false;
            discontinuityUs = readDiscontinuity();
            return discontinuityUs != C0907C.TIME_UNSET ? discontinuityUs : 0;
        } else {
            discontinuityUs = this.mediaPeriod.readDiscontinuity();
            if (discontinuityUs == C0907C.TIME_UNSET) {
                return C0907C.TIME_UNSET;
            }
            boolean z2;
            if (discontinuityUs >= this.startUs) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkState(z2);
            if (this.endUs == Long.MIN_VALUE || discontinuityUs <= this.endUs) {
                z = true;
            }
            Assertions.checkState(z);
            return discontinuityUs - this.startUs;
        }
    }

    public long getBufferedPositionUs() {
        long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
        if (bufferedPositionUs == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        if (this.endUs == Long.MIN_VALUE || bufferedPositionUs < this.endUs) {
            return Math.max(0, bufferedPositionUs - this.startUs);
        }
        return Long.MIN_VALUE;
    }

    public long seekToUs(long positionUs) {
        boolean z = false;
        for (ClippingSampleStream sampleStream : this.sampleStreams) {
            if (sampleStream != null) {
                sampleStream.clearSentEos();
            }
        }
        long seekUs = this.mediaPeriod.seekToUs(this.startUs + positionUs);
        if (seekUs == this.startUs + positionUs || (seekUs >= this.startUs && (this.endUs == Long.MIN_VALUE || seekUs <= this.endUs))) {
            z = true;
        }
        Assertions.checkState(z);
        return seekUs - this.startUs;
    }

    public long getNextLoadPositionUs() {
        long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
        if (nextLoadPositionUs == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        if (this.endUs == Long.MIN_VALUE || nextLoadPositionUs < this.endUs) {
            return nextLoadPositionUs - this.startUs;
        }
        return Long.MIN_VALUE;
    }

    public boolean continueLoading(long positionUs) {
        return this.mediaPeriod.continueLoading(this.startUs + positionUs);
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        boolean z = (this.startUs == C0907C.TIME_UNSET || this.endUs == C0907C.TIME_UNSET) ? false : true;
        Assertions.checkState(z);
        this.callback.onPrepared(this);
    }

    public void onContinueLoadingRequested(MediaPeriod source) {
        this.callback.onContinueLoadingRequested(this);
    }

    private static boolean shouldKeepInitialDiscontinuity(TrackSelection[] selections) {
        for (TrackSelection trackSelection : selections) {
            if (trackSelection != null && !MimeTypes.isAudio(trackSelection.getSelectedFormat().sampleMimeType)) {
                return true;
            }
        }
        return false;
    }
}
