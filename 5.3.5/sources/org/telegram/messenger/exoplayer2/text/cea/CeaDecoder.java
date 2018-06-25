package org.telegram.messenger.exoplayer2.text.cea;

import java.util.LinkedList;
import java.util.PriorityQueue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoder;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.text.SubtitleInputBuffer;
import org.telegram.messenger.exoplayer2.text.SubtitleOutputBuffer;
import org.telegram.messenger.exoplayer2.util.Assertions;

abstract class CeaDecoder implements SubtitleDecoder {
    private static final int NUM_INPUT_BUFFERS = 10;
    private static final int NUM_OUTPUT_BUFFERS = 2;
    private final LinkedList<SubtitleInputBuffer> availableInputBuffers = new LinkedList();
    private final LinkedList<SubtitleOutputBuffer> availableOutputBuffers;
    private SubtitleInputBuffer dequeuedInputBuffer;
    private long playbackPositionUs;
    private final PriorityQueue<SubtitleInputBuffer> queuedInputBuffers;

    protected abstract Subtitle createSubtitle();

    protected abstract void decode(SubtitleInputBuffer subtitleInputBuffer);

    public abstract String getName();

    protected abstract boolean isNewSubtitleDataAvailable();

    public CeaDecoder() {
        int i;
        for (i = 0; i < 10; i++) {
            this.availableInputBuffers.add(new SubtitleInputBuffer());
        }
        this.availableOutputBuffers = new LinkedList();
        for (i = 0; i < 2; i++) {
            this.availableOutputBuffers.add(new CeaOutputBuffer(this));
        }
        this.queuedInputBuffers = new PriorityQueue();
    }

    public void setPositionUs(long positionUs) {
        this.playbackPositionUs = positionUs;
    }

    public SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        Assertions.checkState(this.dequeuedInputBuffer == null);
        if (this.availableInputBuffers.isEmpty()) {
            return null;
        }
        this.dequeuedInputBuffer = (SubtitleInputBuffer) this.availableInputBuffers.pollFirst();
        return this.dequeuedInputBuffer;
    }

    public void queueInputBuffer(SubtitleInputBuffer inputBuffer) throws SubtitleDecoderException {
        Assertions.checkArgument(inputBuffer == this.dequeuedInputBuffer);
        if (inputBuffer.isDecodeOnly()) {
            releaseInputBuffer(inputBuffer);
        } else {
            this.queuedInputBuffers.add(inputBuffer);
        }
        this.dequeuedInputBuffer = null;
    }

    public SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        if (this.availableOutputBuffers.isEmpty()) {
            return null;
        }
        while (!this.queuedInputBuffers.isEmpty() && ((SubtitleInputBuffer) this.queuedInputBuffers.peek()).timeUs <= this.playbackPositionUs) {
            SubtitleInputBuffer inputBuffer = (SubtitleInputBuffer) this.queuedInputBuffers.poll();
            if (inputBuffer.isEndOfStream()) {
                SubtitleOutputBuffer outputBuffer = (SubtitleOutputBuffer) this.availableOutputBuffers.pollFirst();
                outputBuffer.addFlag(4);
                releaseInputBuffer(inputBuffer);
                return outputBuffer;
            }
            decode(inputBuffer);
            if (isNewSubtitleDataAvailable()) {
                Subtitle subtitle = createSubtitle();
                if (!inputBuffer.isDecodeOnly()) {
                    outputBuffer = (SubtitleOutputBuffer) this.availableOutputBuffers.pollFirst();
                    outputBuffer.setContent(inputBuffer.timeUs, subtitle, Long.MAX_VALUE);
                    releaseInputBuffer(inputBuffer);
                    return outputBuffer;
                }
            }
            releaseInputBuffer(inputBuffer);
        }
        return null;
    }

    private void releaseInputBuffer(SubtitleInputBuffer inputBuffer) {
        inputBuffer.clear();
        this.availableInputBuffers.add(inputBuffer);
    }

    protected void releaseOutputBuffer(SubtitleOutputBuffer outputBuffer) {
        outputBuffer.clear();
        this.availableOutputBuffers.add(outputBuffer);
    }

    public void flush() {
        this.playbackPositionUs = 0;
        while (!this.queuedInputBuffers.isEmpty()) {
            releaseInputBuffer((SubtitleInputBuffer) this.queuedInputBuffers.poll());
        }
        if (this.dequeuedInputBuffer != null) {
            releaseInputBuffer(this.dequeuedInputBuffer);
            this.dequeuedInputBuffer = null;
        }
    }

    public void release() {
    }
}
