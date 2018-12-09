package org.telegram.messenger.exoplayer2.text.cea;

import java.util.LinkedList;
import java.util.PriorityQueue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoder;
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

    public CeaDecoder() {
        int i = 0;
        for (int i2 = 0; i2 < 10; i2++) {
            this.availableInputBuffers.add(new SubtitleInputBuffer());
        }
        this.availableOutputBuffers = new LinkedList();
        while (i < 2) {
            this.availableOutputBuffers.add(new CeaOutputBuffer(this));
            i++;
        }
        this.queuedInputBuffers = new PriorityQueue();
    }

    private void releaseInputBuffer(SubtitleInputBuffer subtitleInputBuffer) {
        subtitleInputBuffer.clear();
        this.availableInputBuffers.add(subtitleInputBuffer);
    }

    protected abstract Subtitle createSubtitle();

    protected abstract void decode(SubtitleInputBuffer subtitleInputBuffer);

    public SubtitleInputBuffer dequeueInputBuffer() {
        Assertions.checkState(this.dequeuedInputBuffer == null);
        if (this.availableInputBuffers.isEmpty()) {
            return null;
        }
        this.dequeuedInputBuffer = (SubtitleInputBuffer) this.availableInputBuffers.pollFirst();
        return this.dequeuedInputBuffer;
    }

    public SubtitleOutputBuffer dequeueOutputBuffer() {
        if (this.availableOutputBuffers.isEmpty()) {
            return null;
        }
        while (!this.queuedInputBuffers.isEmpty() && ((SubtitleInputBuffer) this.queuedInputBuffers.peek()).timeUs <= this.playbackPositionUs) {
            SubtitleInputBuffer subtitleInputBuffer = (SubtitleInputBuffer) this.queuedInputBuffers.poll();
            if (subtitleInputBuffer.isEndOfStream()) {
                SubtitleOutputBuffer subtitleOutputBuffer = (SubtitleOutputBuffer) this.availableOutputBuffers.pollFirst();
                subtitleOutputBuffer.addFlag(4);
                releaseInputBuffer(subtitleInputBuffer);
                return subtitleOutputBuffer;
            }
            decode(subtitleInputBuffer);
            if (isNewSubtitleDataAvailable()) {
                Subtitle createSubtitle = createSubtitle();
                if (!subtitleInputBuffer.isDecodeOnly()) {
                    subtitleOutputBuffer = (SubtitleOutputBuffer) this.availableOutputBuffers.pollFirst();
                    subtitleOutputBuffer.setContent(subtitleInputBuffer.timeUs, createSubtitle, Long.MAX_VALUE);
                    releaseInputBuffer(subtitleInputBuffer);
                    return subtitleOutputBuffer;
                }
            }
            releaseInputBuffer(subtitleInputBuffer);
        }
        return null;
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

    public abstract String getName();

    protected abstract boolean isNewSubtitleDataAvailable();

    public void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) {
        Assertions.checkArgument(subtitleInputBuffer == this.dequeuedInputBuffer);
        if (subtitleInputBuffer.isDecodeOnly()) {
            releaseInputBuffer(subtitleInputBuffer);
        } else {
            this.queuedInputBuffers.add(subtitleInputBuffer);
        }
        this.dequeuedInputBuffer = null;
    }

    public void release() {
    }

    protected void releaseOutputBuffer(SubtitleOutputBuffer subtitleOutputBuffer) {
        subtitleOutputBuffer.clear();
        this.availableOutputBuffers.add(subtitleOutputBuffer);
    }

    public void setPositionUs(long j) {
        this.playbackPositionUs = j;
    }
}
