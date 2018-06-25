package org.telegram.messenger.exoplayer2.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class ResamplingAudioProcessor implements AudioProcessor {
    private ByteBuffer buffer = EMPTY_BUFFER;
    private int channelCount = -1;
    private int encoding = 0;
    private boolean inputEnded;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private int sampleRateHz = -1;

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor$UnhandledFormatException {
        if (encoding != 3 && encoding != 2 && encoding != Integer.MIN_VALUE && encoding != 1073741824) {
            throw new AudioProcessor$UnhandledFormatException(sampleRateHz, channelCount, encoding);
        } else if (this.sampleRateHz == sampleRateHz && this.channelCount == channelCount && this.encoding == encoding) {
            return false;
        } else {
            this.sampleRateHz = sampleRateHz;
            this.channelCount = channelCount;
            this.encoding = encoding;
            if (encoding == 2) {
                this.buffer = EMPTY_BUFFER;
            }
            return true;
        }
    }

    public boolean isActive() {
        return (this.encoding == 0 || this.encoding == 2) ? false : true;
    }

    public int getOutputChannelCount() {
        return this.channelCount;
    }

    public int getOutputEncoding() {
        return 2;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        int resampledSize;
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int size = limit - position;
        switch (this.encoding) {
            case Integer.MIN_VALUE:
                resampledSize = (size / 3) * 2;
                break;
            case 3:
                resampledSize = size * 2;
                break;
            case 1073741824:
                resampledSize = size / 2;
                break;
            default:
                throw new IllegalStateException();
        }
        if (this.buffer.capacity() < resampledSize) {
            this.buffer = ByteBuffer.allocateDirect(resampledSize).order(ByteOrder.nativeOrder());
        } else {
            this.buffer.clear();
        }
        int i;
        switch (this.encoding) {
            case Integer.MIN_VALUE:
                for (i = position; i < limit; i += 3) {
                    this.buffer.put(inputBuffer.get(i + 1));
                    this.buffer.put(inputBuffer.get(i + 2));
                }
                break;
            case 3:
                for (i = position; i < limit; i++) {
                    this.buffer.put((byte) 0);
                    this.buffer.put((byte) ((inputBuffer.get(i) & 255) - 128));
                }
                break;
            case 1073741824:
                for (i = position; i < limit; i += 4) {
                    this.buffer.put(inputBuffer.get(i + 2));
                    this.buffer.put(inputBuffer.get(i + 3));
                }
                break;
            default:
                throw new IllegalStateException();
        }
        inputBuffer.position(inputBuffer.limit());
        this.buffer.flip();
        this.outputBuffer = this.buffer;
    }

    public void queueEndOfStream() {
        this.inputEnded = true;
    }

    public ByteBuffer getOutput() {
        ByteBuffer outputBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return outputBuffer;
    }

    public boolean isEnded() {
        return this.inputEnded && this.outputBuffer == EMPTY_BUFFER;
    }

    public void flush() {
        this.outputBuffer = EMPTY_BUFFER;
        this.inputEnded = false;
    }

    public void reset() {
        flush();
        this.buffer = EMPTY_BUFFER;
        this.sampleRateHz = -1;
        this.channelCount = -1;
        this.encoding = 0;
    }
}
