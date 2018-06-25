package org.telegram.messenger.exoplayer2.text;

import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.decoder.SimpleDecoder;

public abstract class SimpleSubtitleDecoder extends SimpleDecoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> implements SubtitleDecoder {
    private final String name;

    protected SimpleSubtitleDecoder(String str) {
        super(new SubtitleInputBuffer[2], new SubtitleOutputBuffer[2]);
        this.name = str;
        setInitialInputBufferSize(1024);
    }

    protected final SubtitleInputBuffer createInputBuffer() {
        return new SubtitleInputBuffer();
    }

    protected final SubtitleOutputBuffer createOutputBuffer() {
        return new SimpleSubtitleOutputBuffer(this);
    }

    protected abstract Subtitle decode(byte[] bArr, int i, boolean z);

    protected final SubtitleDecoderException decode(SubtitleInputBuffer subtitleInputBuffer, SubtitleOutputBuffer subtitleOutputBuffer, boolean z) {
        try {
            ByteBuffer byteBuffer = subtitleInputBuffer.data;
            Subtitle decode = decode(byteBuffer.array(), byteBuffer.limit(), z);
            subtitleOutputBuffer.setContent(subtitleInputBuffer.timeUs, decode, subtitleInputBuffer.subsampleOffsetUs);
            subtitleOutputBuffer.clearFlag(Integer.MIN_VALUE);
            return null;
        } catch (SubtitleDecoderException e) {
            return e;
        }
    }

    public final String getName() {
        return this.name;
    }

    protected final void releaseOutputBuffer(SubtitleOutputBuffer subtitleOutputBuffer) {
        super.releaseOutputBuffer(subtitleOutputBuffer);
    }

    public void setPositionUs(long j) {
    }
}
