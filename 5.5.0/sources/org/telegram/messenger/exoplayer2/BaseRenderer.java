package org.telegram.messenger.exoplayer2;

import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MediaClock;

public abstract class BaseRenderer implements Renderer, RendererCapabilities {
    private RendererConfiguration configuration;
    private int index;
    private boolean readEndOfStream = true;
    private int state;
    private SampleStream stream;
    private boolean streamIsFinal;
    private long streamOffsetUs;
    private final int trackType;

    public BaseRenderer(int i) {
        this.trackType = i;
    }

    public final void disable() {
        boolean z = true;
        if (this.state != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.state = 0;
        this.stream = null;
        this.streamIsFinal = false;
        onDisabled();
    }

    public final void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j, boolean z, long j2) {
        Assertions.checkState(this.state == 0);
        this.configuration = rendererConfiguration;
        this.state = 1;
        onEnabled(z);
        replaceStream(formatArr, sampleStream, j2);
        onPositionReset(j, z);
    }

    public final RendererCapabilities getCapabilities() {
        return this;
    }

    protected final RendererConfiguration getConfiguration() {
        return this.configuration;
    }

    protected final int getIndex() {
        return this.index;
    }

    public MediaClock getMediaClock() {
        return null;
    }

    public final int getState() {
        return this.state;
    }

    public final SampleStream getStream() {
        return this.stream;
    }

    public final int getTrackType() {
        return this.trackType;
    }

    public void handleMessage(int i, Object obj) {
    }

    public final boolean hasReadStreamToEnd() {
        return this.readEndOfStream;
    }

    public final boolean isCurrentStreamFinal() {
        return this.streamIsFinal;
    }

    protected final boolean isSourceReady() {
        return this.readEndOfStream ? this.streamIsFinal : this.stream.isReady();
    }

    public final void maybeThrowStreamError() {
        this.stream.maybeThrowError();
    }

    protected void onDisabled() {
    }

    protected void onEnabled(boolean z) {
    }

    protected void onPositionReset(long j, boolean z) {
    }

    protected void onStarted() {
    }

    protected void onStopped() {
    }

    protected void onStreamChanged(Format[] formatArr, long j) {
    }

    protected final int readSource(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        int readData = this.stream.readData(formatHolder, decoderInputBuffer, z);
        if (readData == -4) {
            if (decoderInputBuffer.isEndOfStream()) {
                this.readEndOfStream = true;
                return this.streamIsFinal ? -4 : -3;
            } else {
                decoderInputBuffer.timeUs += this.streamOffsetUs;
            }
        } else if (readData == -5) {
            Format format = formatHolder.format;
            if (format.subsampleOffsetUs != Long.MAX_VALUE) {
                formatHolder.format = format.copyWithSubsampleOffsetUs(format.subsampleOffsetUs + this.streamOffsetUs);
            }
        }
        return readData;
    }

    public final void replaceStream(Format[] formatArr, SampleStream sampleStream, long j) {
        Assertions.checkState(!this.streamIsFinal);
        this.stream = sampleStream;
        this.readEndOfStream = false;
        this.streamOffsetUs = j;
        onStreamChanged(formatArr, j);
    }

    public final void resetPosition(long j) {
        this.streamIsFinal = false;
        this.readEndOfStream = false;
        onPositionReset(j, false);
    }

    public final void setCurrentStreamFinal() {
        this.streamIsFinal = true;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    protected void skipSource(long j) {
        this.stream.skipData(j - this.streamOffsetUs);
    }

    public final void start() {
        boolean z = true;
        if (this.state != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.state = 2;
        onStarted();
    }

    public final void stop() {
        Assertions.checkState(this.state == 2);
        this.state = 1;
        onStopped();
    }

    public int supportsMixedMimeTypeAdaptation() {
        return 0;
    }
}
