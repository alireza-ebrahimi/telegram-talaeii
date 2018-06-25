package org.telegram.messenger.exoplayer2.text;

import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.decoder.SimpleDecoder;

public abstract class SimpleSubtitleDecoder extends SimpleDecoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> implements SubtitleDecoder {
    private final String name;

    protected abstract Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException;

    protected SimpleSubtitleDecoder(String name) {
        super(new SubtitleInputBuffer[2], new SubtitleOutputBuffer[2]);
        this.name = name;
        setInitialInputBufferSize(1024);
    }

    public final String getName() {
        return this.name;
    }

    public void setPositionUs(long timeUs) {
    }

    protected final SubtitleInputBuffer createInputBuffer() {
        return new SubtitleInputBuffer();
    }

    protected final SubtitleOutputBuffer createOutputBuffer() {
        return new SimpleSubtitleOutputBuffer(this);
    }

    protected final void releaseOutputBuffer(SubtitleOutputBuffer buffer) {
        super.releaseOutputBuffer(buffer);
    }

    protected final SubtitleDecoderException decode(SubtitleInputBuffer inputBuffer, SubtitleOutputBuffer outputBuffer, boolean reset) {
        try {
            ByteBuffer inputData = inputBuffer.data;
            SubtitleOutputBuffer subtitleOutputBuffer = outputBuffer;
            subtitleOutputBuffer.setContent(inputBuffer.timeUs, decode(inputData.array(), inputData.limit(), reset), inputBuffer.subsampleOffsetUs);
            outputBuffer.clearFlag(Integer.MIN_VALUE);
            return null;
        } catch (SubtitleDecoderException e) {
            return e;
        }
    }
}
