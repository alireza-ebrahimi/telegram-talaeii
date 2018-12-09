package org.telegram.messenger.exoplayer2.audio;

import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.audio.AudioProcessor.UnhandledFormatException;

final class ResamplingAudioProcessor implements AudioProcessor {
    private ByteBuffer buffer = EMPTY_BUFFER;
    private int channelCount = -1;
    private int encoding = 0;
    private boolean inputEnded;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private int sampleRateHz = -1;

    public boolean configure(int i, int i2, int i3) {
        if (i3 != 3 && i3 != 2 && i3 != Integer.MIN_VALUE && i3 != 1073741824) {
            throw new UnhandledFormatException(i, i2, i3);
        } else if (this.sampleRateHz == i && this.channelCount == i2 && this.encoding == i3) {
            return false;
        } else {
            this.sampleRateHz = i;
            this.channelCount = i2;
            this.encoding = i3;
            if (i3 == 2) {
                this.buffer = EMPTY_BUFFER;
            }
            return true;
        }
    }

    public void flush() {
        this.outputBuffer = EMPTY_BUFFER;
        this.inputEnded = false;
    }

    public ByteBuffer getOutput() {
        ByteBuffer byteBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return byteBuffer;
    }

    public int getOutputChannelCount() {
        return this.channelCount;
    }

    public int getOutputEncoding() {
        return 2;
    }

    public boolean isActive() {
        return (this.encoding == 0 || this.encoding == 2) ? false : true;
    }

    public boolean isEnded() {
        return this.inputEnded && this.outputBuffer == EMPTY_BUFFER;
    }

    public void queueEndOfStream() {
        this.inputEnded = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void queueInput(java.nio.ByteBuffer r5) {
        /*
        r4 = this;
        r1 = r5.position();
        r2 = r5.limit();
        r0 = r2 - r1;
        r3 = r4.encoding;
        switch(r3) {
            case -2147483648: goto L_0x0038;
            case 3: goto L_0x0015;
            case 1073741824: goto L_0x003d;
            default: goto L_0x000f;
        };
    L_0x000f:
        r0 = new java.lang.IllegalStateException;
        r0.<init>();
        throw r0;
    L_0x0015:
        r0 = r0 * 2;
    L_0x0017:
        r3 = r4.buffer;
        r3 = r3.capacity();
        if (r3 >= r0) goto L_0x0040;
    L_0x001f:
        r0 = java.nio.ByteBuffer.allocateDirect(r0);
        r3 = java.nio.ByteOrder.nativeOrder();
        r0 = r0.order(r3);
        r4.buffer = r0;
    L_0x002d:
        r0 = r4.encoding;
        switch(r0) {
            case -2147483648: goto L_0x005f;
            case 3: goto L_0x0046;
            case 1073741824: goto L_0x007a;
            default: goto L_0x0032;
        };
    L_0x0032:
        r0 = new java.lang.IllegalStateException;
        r0.<init>();
        throw r0;
    L_0x0038:
        r0 = r0 / 3;
        r0 = r0 * 2;
        goto L_0x0017;
    L_0x003d:
        r0 = r0 / 2;
        goto L_0x0017;
    L_0x0040:
        r0 = r4.buffer;
        r0.clear();
        goto L_0x002d;
    L_0x0046:
        if (r1 >= r2) goto L_0x0096;
    L_0x0048:
        r0 = r4.buffer;
        r3 = 0;
        r0.put(r3);
        r0 = r4.buffer;
        r3 = r5.get(r1);
        r3 = r3 & 255;
        r3 = r3 + -128;
        r3 = (byte) r3;
        r0.put(r3);
        r1 = r1 + 1;
        goto L_0x0046;
    L_0x005f:
        if (r1 >= r2) goto L_0x0096;
    L_0x0061:
        r0 = r4.buffer;
        r3 = r1 + 1;
        r3 = r5.get(r3);
        r0.put(r3);
        r0 = r4.buffer;
        r3 = r1 + 2;
        r3 = r5.get(r3);
        r0.put(r3);
        r1 = r1 + 3;
        goto L_0x005f;
    L_0x007a:
        r0 = r1;
    L_0x007b:
        if (r0 >= r2) goto L_0x0096;
    L_0x007d:
        r1 = r4.buffer;
        r3 = r0 + 2;
        r3 = r5.get(r3);
        r1.put(r3);
        r1 = r4.buffer;
        r3 = r0 + 3;
        r3 = r5.get(r3);
        r1.put(r3);
        r0 = r0 + 4;
        goto L_0x007b;
    L_0x0096:
        r0 = r5.limit();
        r5.position(r0);
        r0 = r4.buffer;
        r0.flip();
        r0 = r4.buffer;
        r4.outputBuffer = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.audio.ResamplingAudioProcessor.queueInput(java.nio.ByteBuffer):void");
    }

    public void reset() {
        flush();
        this.buffer = EMPTY_BUFFER;
        this.sampleRateHz = -1;
        this.channelCount = -1;
        this.encoding = 0;
    }
}
