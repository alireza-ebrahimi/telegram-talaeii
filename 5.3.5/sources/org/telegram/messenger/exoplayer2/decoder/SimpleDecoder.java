package org.telegram.messenger.exoplayer2.decoder;

import java.util.LinkedList;
import org.telegram.messenger.exoplayer2.util.Assertions;

public abstract class SimpleDecoder<I extends DecoderInputBuffer, O extends OutputBuffer, E extends Exception> implements Decoder<I, O, E> {
    private int availableInputBufferCount;
    private final I[] availableInputBuffers;
    private int availableOutputBufferCount;
    private final O[] availableOutputBuffers;
    private final Thread decodeThread;
    private I dequeuedInputBuffer;
    private E exception;
    private boolean flushed;
    private final Object lock = new Object();
    private final LinkedList<I> queuedInputBuffers = new LinkedList();
    private final LinkedList<O> queuedOutputBuffers = new LinkedList();
    private boolean released;
    private int skippedOutputBufferCount;

    /* renamed from: org.telegram.messenger.exoplayer2.decoder.SimpleDecoder$1 */
    class C17111 extends Thread {
        C17111() {
        }

        public void run() {
            SimpleDecoder.this.run();
        }
    }

    protected abstract I createInputBuffer();

    protected abstract O createOutputBuffer();

    protected abstract E decode(I i, O o, boolean z);

    protected SimpleDecoder(I[] inputBuffers, O[] outputBuffers) {
        int i;
        this.availableInputBuffers = inputBuffers;
        this.availableInputBufferCount = inputBuffers.length;
        for (i = 0; i < this.availableInputBufferCount; i++) {
            this.availableInputBuffers[i] = createInputBuffer();
        }
        this.availableOutputBuffers = outputBuffers;
        this.availableOutputBufferCount = outputBuffers.length;
        for (i = 0; i < this.availableOutputBufferCount; i++) {
            this.availableOutputBuffers[i] = createOutputBuffer();
        }
        this.decodeThread = new C17111();
        this.decodeThread.start();
    }

    protected final void setInitialInputBufferSize(int size) {
        boolean z;
        int i = 0;
        if (this.availableInputBufferCount == this.availableInputBuffers.length) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        DecoderInputBuffer[] decoderInputBufferArr = this.availableInputBuffers;
        int length = decoderInputBufferArr.length;
        while (i < length) {
            decoderInputBufferArr[i].ensureSpaceForWrite(size);
            i++;
        }
    }

    public final I dequeueInputBuffer() throws Exception {
        I i;
        synchronized (this.lock) {
            DecoderInputBuffer decoderInputBuffer;
            maybeThrowException();
            Assertions.checkState(this.dequeuedInputBuffer == null);
            if (this.availableInputBufferCount == 0) {
                decoderInputBuffer = null;
            } else {
                DecoderInputBuffer[] decoderInputBufferArr = this.availableInputBuffers;
                int i2 = this.availableInputBufferCount - 1;
                this.availableInputBufferCount = i2;
                decoderInputBuffer = decoderInputBufferArr[i2];
            }
            this.dequeuedInputBuffer = decoderInputBuffer;
            i = this.dequeuedInputBuffer;
        }
        return i;
    }

    public final void queueInputBuffer(I inputBuffer) throws Exception {
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkArgument(inputBuffer == this.dequeuedInputBuffer);
            this.queuedInputBuffers.addLast(inputBuffer);
            maybeNotifyDecodeLoop();
            this.dequeuedInputBuffer = null;
        }
    }

    public final O dequeueOutputBuffer() throws Exception {
        O o;
        synchronized (this.lock) {
            maybeThrowException();
            if (this.queuedOutputBuffers.isEmpty()) {
                o = null;
            } else {
                OutputBuffer outputBuffer = (OutputBuffer) this.queuedOutputBuffers.removeFirst();
            }
        }
        return o;
    }

    protected void releaseOutputBuffer(O outputBuffer) {
        synchronized (this.lock) {
            releaseOutputBufferInternal(outputBuffer);
            maybeNotifyDecodeLoop();
        }
    }

    public final void flush() {
        synchronized (this.lock) {
            this.flushed = true;
            this.skippedOutputBufferCount = 0;
            if (this.dequeuedInputBuffer != null) {
                releaseInputBufferInternal(this.dequeuedInputBuffer);
                this.dequeuedInputBuffer = null;
            }
            while (!this.queuedInputBuffers.isEmpty()) {
                releaseInputBufferInternal((DecoderInputBuffer) this.queuedInputBuffers.removeFirst());
            }
            while (!this.queuedOutputBuffers.isEmpty()) {
                releaseOutputBufferInternal((OutputBuffer) this.queuedOutputBuffers.removeFirst());
            }
        }
    }

    public void release() {
        synchronized (this.lock) {
            this.released = true;
            this.lock.notify();
        }
        try {
            this.decodeThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void maybeThrowException() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        }
    }

    private void maybeNotifyDecodeLoop() {
        if (canDecodeBuffer()) {
            this.lock.notify();
        }
    }

    private void run() {
        do {
            try {
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        } while (decode());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean decode() throws java.lang.InterruptedException {
        /*
        r7 = this;
        r3 = 0;
        r4 = r7.lock;
        monitor-enter(r4);
    L_0x0004:
        r5 = r7.released;	 Catch:{ all -> 0x0014 }
        if (r5 != 0) goto L_0x0017;
    L_0x0008:
        r5 = r7.canDecodeBuffer();	 Catch:{ all -> 0x0014 }
        if (r5 != 0) goto L_0x0017;
    L_0x000e:
        r5 = r7.lock;	 Catch:{ all -> 0x0014 }
        r5.wait();	 Catch:{ all -> 0x0014 }
        goto L_0x0004;
    L_0x0014:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0014 }
        throw r3;
    L_0x0017:
        r5 = r7.released;	 Catch:{ all -> 0x0014 }
        if (r5 == 0) goto L_0x001d;
    L_0x001b:
        monitor-exit(r4);	 Catch:{ all -> 0x0014 }
    L_0x001c:
        return r3;
    L_0x001d:
        r5 = r7.queuedInputBuffers;	 Catch:{ all -> 0x0014 }
        r0 = r5.removeFirst();	 Catch:{ all -> 0x0014 }
        r0 = (org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer) r0;	 Catch:{ all -> 0x0014 }
        r5 = r7.availableOutputBuffers;	 Catch:{ all -> 0x0014 }
        r6 = r7.availableOutputBufferCount;	 Catch:{ all -> 0x0014 }
        r6 = r6 + -1;
        r7.availableOutputBufferCount = r6;	 Catch:{ all -> 0x0014 }
        r1 = r5[r6];	 Catch:{ all -> 0x0014 }
        r2 = r7.flushed;	 Catch:{ all -> 0x0014 }
        r5 = 0;
        r7.flushed = r5;	 Catch:{ all -> 0x0014 }
        monitor-exit(r4);	 Catch:{ all -> 0x0014 }
        r4 = r0.isEndOfStream();
        if (r4 == 0) goto L_0x004f;
    L_0x003b:
        r3 = 4;
        r1.addFlag(r3);
    L_0x003f:
        r4 = r7.lock;
        monitor-enter(r4);
        r3 = r7.flushed;	 Catch:{ all -> 0x007c }
        if (r3 == 0) goto L_0x006c;
    L_0x0046:
        r7.releaseOutputBufferInternal(r1);	 Catch:{ all -> 0x007c }
    L_0x0049:
        r7.releaseInputBufferInternal(r0);	 Catch:{ all -> 0x007c }
        monitor-exit(r4);	 Catch:{ all -> 0x007c }
        r3 = 1;
        goto L_0x001c;
    L_0x004f:
        r4 = r0.isDecodeOnly();
        if (r4 == 0) goto L_0x005a;
    L_0x0055:
        r4 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r1.addFlag(r4);
    L_0x005a:
        r4 = r7.decode(r0, r1, r2);
        r7.exception = r4;
        r4 = r7.exception;
        if (r4 == 0) goto L_0x003f;
    L_0x0064:
        r4 = r7.lock;
        monitor-enter(r4);
        monitor-exit(r4);	 Catch:{ all -> 0x0069 }
        goto L_0x001c;
    L_0x0069:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0069 }
        throw r3;
    L_0x006c:
        r3 = r1.isDecodeOnly();	 Catch:{ all -> 0x007c }
        if (r3 == 0) goto L_0x007f;
    L_0x0072:
        r3 = r7.skippedOutputBufferCount;	 Catch:{ all -> 0x007c }
        r3 = r3 + 1;
        r7.skippedOutputBufferCount = r3;	 Catch:{ all -> 0x007c }
        r7.releaseOutputBufferInternal(r1);	 Catch:{ all -> 0x007c }
        goto L_0x0049;
    L_0x007c:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x007c }
        throw r3;
    L_0x007f:
        r3 = r7.skippedOutputBufferCount;	 Catch:{ all -> 0x007c }
        r1.skippedOutputBufferCount = r3;	 Catch:{ all -> 0x007c }
        r3 = 0;
        r7.skippedOutputBufferCount = r3;	 Catch:{ all -> 0x007c }
        r3 = r7.queuedOutputBuffers;	 Catch:{ all -> 0x007c }
        r3.addLast(r1);	 Catch:{ all -> 0x007c }
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.decoder.SimpleDecoder.decode():boolean");
    }

    private boolean canDecodeBuffer() {
        return !this.queuedInputBuffers.isEmpty() && this.availableOutputBufferCount > 0;
    }

    private void releaseInputBufferInternal(I inputBuffer) {
        inputBuffer.clear();
        DecoderInputBuffer[] decoderInputBufferArr = this.availableInputBuffers;
        int i = this.availableInputBufferCount;
        this.availableInputBufferCount = i + 1;
        decoderInputBufferArr[i] = inputBuffer;
    }

    private void releaseOutputBufferInternal(O outputBuffer) {
        outputBuffer.clear();
        OutputBuffer[] outputBufferArr = this.availableOutputBuffers;
        int i = this.availableOutputBufferCount;
        this.availableOutputBufferCount = i + 1;
        outputBufferArr[i] = outputBuffer;
    }
}
