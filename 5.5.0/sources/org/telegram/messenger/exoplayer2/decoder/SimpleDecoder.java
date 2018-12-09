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
    class C34661 extends Thread {
        C34661() {
        }

        public void run() {
            SimpleDecoder.this.run();
        }
    }

    protected SimpleDecoder(I[] iArr, O[] oArr) {
        int i = 0;
        this.availableInputBuffers = iArr;
        this.availableInputBufferCount = iArr.length;
        for (int i2 = 0; i2 < this.availableInputBufferCount; i2++) {
            this.availableInputBuffers[i2] = createInputBuffer();
        }
        this.availableOutputBuffers = oArr;
        this.availableOutputBufferCount = oArr.length;
        while (i < this.availableOutputBufferCount) {
            this.availableOutputBuffers[i] = createOutputBuffer();
            i++;
        }
        this.decodeThread = new C34661();
        this.decodeThread.start();
    }

    private boolean canDecodeBuffer() {
        return !this.queuedInputBuffers.isEmpty() && this.availableOutputBufferCount > 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean decode() {
        /*
        r6 = this;
        r1 = 0;
        r2 = r6.lock;
        monitor-enter(r2);
    L_0x0004:
        r0 = r6.released;	 Catch:{ all -> 0x0014 }
        if (r0 != 0) goto L_0x0017;
    L_0x0008:
        r0 = r6.canDecodeBuffer();	 Catch:{ all -> 0x0014 }
        if (r0 != 0) goto L_0x0017;
    L_0x000e:
        r0 = r6.lock;	 Catch:{ all -> 0x0014 }
        r0.wait();	 Catch:{ all -> 0x0014 }
        goto L_0x0004;
    L_0x0014:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0014 }
        throw r0;
    L_0x0017:
        r0 = r6.released;	 Catch:{ all -> 0x0014 }
        if (r0 == 0) goto L_0x001e;
    L_0x001b:
        monitor-exit(r2);	 Catch:{ all -> 0x0014 }
        r0 = r1;
    L_0x001d:
        return r0;
    L_0x001e:
        r0 = r6.queuedInputBuffers;	 Catch:{ all -> 0x0014 }
        r0 = r0.removeFirst();	 Catch:{ all -> 0x0014 }
        r0 = (org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer) r0;	 Catch:{ all -> 0x0014 }
        r3 = r6.availableOutputBuffers;	 Catch:{ all -> 0x0014 }
        r4 = r6.availableOutputBufferCount;	 Catch:{ all -> 0x0014 }
        r4 = r4 + -1;
        r6.availableOutputBufferCount = r4;	 Catch:{ all -> 0x0014 }
        r3 = r3[r4];	 Catch:{ all -> 0x0014 }
        r4 = r6.flushed;	 Catch:{ all -> 0x0014 }
        r5 = 0;
        r6.flushed = r5;	 Catch:{ all -> 0x0014 }
        monitor-exit(r2);	 Catch:{ all -> 0x0014 }
        r2 = r0.isEndOfStream();
        if (r2 == 0) goto L_0x0050;
    L_0x003c:
        r1 = 4;
        r3.addFlag(r1);
    L_0x0040:
        r1 = r6.lock;
        monitor-enter(r1);
        r2 = r6.flushed;	 Catch:{ all -> 0x007e }
        if (r2 == 0) goto L_0x006e;
    L_0x0047:
        r6.releaseOutputBufferInternal(r3);	 Catch:{ all -> 0x007e }
    L_0x004a:
        r6.releaseInputBufferInternal(r0);	 Catch:{ all -> 0x007e }
        monitor-exit(r1);	 Catch:{ all -> 0x007e }
        r0 = 1;
        goto L_0x001d;
    L_0x0050:
        r2 = r0.isDecodeOnly();
        if (r2 == 0) goto L_0x005b;
    L_0x0056:
        r2 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r3.addFlag(r2);
    L_0x005b:
        r2 = r6.decode(r0, r3, r4);
        r6.exception = r2;
        r2 = r6.exception;
        if (r2 == 0) goto L_0x0040;
    L_0x0065:
        r2 = r6.lock;
        monitor-enter(r2);
        monitor-exit(r2);	 Catch:{ all -> 0x006b }
        r0 = r1;
        goto L_0x001d;
    L_0x006b:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x006b }
        throw r0;
    L_0x006e:
        r2 = r3.isDecodeOnly();	 Catch:{ all -> 0x007e }
        if (r2 == 0) goto L_0x0081;
    L_0x0074:
        r2 = r6.skippedOutputBufferCount;	 Catch:{ all -> 0x007e }
        r2 = r2 + 1;
        r6.skippedOutputBufferCount = r2;	 Catch:{ all -> 0x007e }
        r6.releaseOutputBufferInternal(r3);	 Catch:{ all -> 0x007e }
        goto L_0x004a;
    L_0x007e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x007e }
        throw r0;
    L_0x0081:
        r2 = r6.skippedOutputBufferCount;	 Catch:{ all -> 0x007e }
        r3.skippedOutputBufferCount = r2;	 Catch:{ all -> 0x007e }
        r2 = 0;
        r6.skippedOutputBufferCount = r2;	 Catch:{ all -> 0x007e }
        r2 = r6.queuedOutputBuffers;	 Catch:{ all -> 0x007e }
        r2.addLast(r3);	 Catch:{ all -> 0x007e }
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.decoder.SimpleDecoder.decode():boolean");
    }

    private void maybeNotifyDecodeLoop() {
        if (canDecodeBuffer()) {
            this.lock.notify();
        }
    }

    private void maybeThrowException() {
        if (this.exception != null) {
            throw this.exception;
        }
    }

    private void releaseInputBufferInternal(I i) {
        i.clear();
        DecoderInputBuffer[] decoderInputBufferArr = this.availableInputBuffers;
        int i2 = this.availableInputBufferCount;
        this.availableInputBufferCount = i2 + 1;
        decoderInputBufferArr[i2] = i;
    }

    private void releaseOutputBufferInternal(O o) {
        o.clear();
        OutputBuffer[] outputBufferArr = this.availableOutputBuffers;
        int i = this.availableOutputBufferCount;
        this.availableOutputBufferCount = i + 1;
        outputBufferArr[i] = o;
    }

    private void run() {
        do {
            try {
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        } while (decode());
    }

    protected abstract I createInputBuffer();

    protected abstract O createOutputBuffer();

    protected abstract E decode(I i, O o, boolean z);

    public final I dequeueInputBuffer() {
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

    public final O dequeueOutputBuffer() {
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

    public final void queueInputBuffer(I i) {
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkArgument(i == this.dequeuedInputBuffer);
            this.queuedInputBuffers.addLast(i);
            maybeNotifyDecodeLoop();
            this.dequeuedInputBuffer = null;
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

    protected void releaseOutputBuffer(O o) {
        synchronized (this.lock) {
            releaseOutputBufferInternal(o);
            maybeNotifyDecodeLoop();
        }
    }

    protected final void setInitialInputBufferSize(int i) {
        int i2 = 0;
        Assertions.checkState(this.availableInputBufferCount == this.availableInputBuffers.length);
        DecoderInputBuffer[] decoderInputBufferArr = this.availableInputBuffers;
        int length = decoderInputBufferArr.length;
        while (i2 < length) {
            decoderInputBufferArr[i2].ensureSpaceForWrite(i);
            i2++;
        }
    }
}
