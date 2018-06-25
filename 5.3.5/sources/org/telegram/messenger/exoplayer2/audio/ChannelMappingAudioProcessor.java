package org.telegram.messenger.exoplayer2.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

final class ChannelMappingAudioProcessor implements AudioProcessor {
    private boolean active;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private int channelCount = -1;
    private boolean inputEnded;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private int[] outputChannels;
    private int[] pendingOutputChannels;
    private int sampleRateHz = -1;

    public void setChannelMap(int[] outputChannels) {
        this.pendingOutputChannels = outputChannels;
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor$UnhandledFormatException {
        boolean outputChannelsChanged = !Arrays.equals(this.pendingOutputChannels, this.outputChannels);
        this.outputChannels = this.pendingOutputChannels;
        if (this.outputChannels == null) {
            this.active = false;
            return outputChannelsChanged;
        } else if (encoding != 2) {
            throw new AudioProcessor$UnhandledFormatException(sampleRateHz, channelCount, encoding);
        } else if (!outputChannelsChanged && this.sampleRateHz == sampleRateHz && this.channelCount == channelCount) {
            return false;
        } else {
            boolean z;
            this.sampleRateHz = sampleRateHz;
            this.channelCount = channelCount;
            if (channelCount != this.outputChannels.length) {
                z = true;
            } else {
                z = false;
            }
            this.active = z;
            int i = 0;
            while (i < this.outputChannels.length) {
                int channelIndex = this.outputChannels[i];
                if (channelIndex >= channelCount) {
                    throw new AudioProcessor$UnhandledFormatException(sampleRateHz, channelCount, encoding);
                }
                this.active = (channelIndex != i ? 1 : 0) | this.active;
                i++;
            }
            return true;
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public int getOutputChannelCount() {
        return this.outputChannels == null ? this.channelCount : this.outputChannels.length;
    }

    public int getOutputEncoding() {
        return 2;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int outputSize = (this.outputChannels.length * ((limit - position) / (this.channelCount * 2))) * 2;
        if (this.buffer.capacity() < outputSize) {
            this.buffer = ByteBuffer.allocateDirect(outputSize).order(ByteOrder.nativeOrder());
        } else {
            this.buffer.clear();
        }
        while (position < limit) {
            for (int channelIndex : this.outputChannels) {
                this.buffer.putShort(inputBuffer.getShort((channelIndex * 2) + position));
            }
            position += this.channelCount * 2;
        }
        inputBuffer.position(limit);
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
        this.channelCount = -1;
        this.sampleRateHz = -1;
        this.outputChannels = null;
        this.active = false;
    }
}
