package com.persianswitch.okhttp3.internal.io;

import com.persianswitch.okio.Buffer;
import com.persianswitch.okio.Sink;
import com.persianswitch.okio.Source;
import com.persianswitch.okio.Timeout;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.telegram.messenger.exoplayer2.C0907C;

public final class Pipe {
    final Buffer buffer = new Buffer();
    final long maxBufferSize;
    public final Sink sink = new PipeSink();
    boolean sinkClosed;
    public final Source source = new PipeSource();
    boolean sourceClosed;

    final class PipeSink implements Sink {
        final Timeout timeout = new Timeout();

        PipeSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                while (byteCount > 0) {
                    if (Pipe.this.sourceClosed) {
                        throw new IOException("source is closed");
                    }
                    long bufferSpaceAvailable = Pipe.this.maxBufferSize - Pipe.this.buffer.size();
                    if (bufferSpaceAvailable == 0) {
                        Pipe.waitUntilNotified(Pipe.this.buffer, this.timeout);
                    } else {
                        long bytesToWrite = Math.min(bufferSpaceAvailable, byteCount);
                        Pipe.this.buffer.write(source, bytesToWrite);
                        byteCount -= bytesToWrite;
                        Pipe.this.buffer.notifyAll();
                    }
                }
            }
        }

        public void flush() throws IOException {
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.buffer.size() > 0) {
                    if (Pipe.this.sourceClosed) {
                        throw new IOException("source is closed");
                    }
                    Pipe.waitUntilNotified(Pipe.this.buffer, this.timeout);
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
            r4 = this;
            r0 = com.persianswitch.okhttp3.internal.io.Pipe.this;
            r1 = r0.buffer;
            monitor-enter(r1);
            r0 = com.persianswitch.okhttp3.internal.io.Pipe.this;	 Catch:{ all -> 0x001e }
            r0 = r0.sinkClosed;	 Catch:{ all -> 0x001e }
            if (r0 == 0) goto L_0x000d;
        L_0x000b:
            monitor-exit(r1);	 Catch:{ all -> 0x001e }
        L_0x000c:
            return;
        L_0x000d:
            r4.flush();	 Catch:{ all -> 0x0021 }
            r0 = com.persianswitch.okhttp3.internal.io.Pipe.this;	 Catch:{ all -> 0x001e }
            r2 = 1;
            r0.sinkClosed = r2;	 Catch:{ all -> 0x001e }
            r0 = com.persianswitch.okhttp3.internal.io.Pipe.this;	 Catch:{ all -> 0x001e }
            r0 = r0.buffer;	 Catch:{ all -> 0x001e }
            r0.notifyAll();	 Catch:{ all -> 0x001e }
            monitor-exit(r1);	 Catch:{ all -> 0x001e }
            goto L_0x000c;
        L_0x001e:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x001e }
            throw r0;
        L_0x0021:
            r0 = move-exception;
            r2 = com.persianswitch.okhttp3.internal.io.Pipe.this;	 Catch:{ all -> 0x001e }
            r3 = 1;
            r2.sinkClosed = r3;	 Catch:{ all -> 0x001e }
            r2 = com.persianswitch.okhttp3.internal.io.Pipe.this;	 Catch:{ all -> 0x001e }
            r2 = r2.buffer;	 Catch:{ all -> 0x001e }
            r2.notifyAll();	 Catch:{ all -> 0x001e }
            throw r0;	 Catch:{ all -> 0x001e }
            */
            throw new UnsupportedOperationException("Method not decompiled: com.persianswitch.okhttp3.internal.io.Pipe.PipeSink.close():void");
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    final class PipeSource implements Source {
        final Timeout timeout = new Timeout();

        PipeSource() {
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            long j;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sourceClosed) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.buffer.size() == 0) {
                    if (Pipe.this.sinkClosed) {
                        j = -1;
                        break;
                    }
                    Pipe.waitUntilNotified(Pipe.this.buffer, this.timeout);
                }
                j = Pipe.this.buffer.read(sink, byteCount);
                Pipe.this.buffer.notifyAll();
            }
            return j;
        }

        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                Pipe.this.sourceClosed = true;
                Pipe.this.buffer.notifyAll();
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    public Pipe(long maxBufferSize) {
        if (maxBufferSize < 1) {
            throw new IllegalArgumentException("maxBufferSize < 1: " + maxBufferSize);
        }
        this.maxBufferSize = maxBufferSize;
    }

    static void waitUntilNotified(Object monitor, Timeout timeout) throws IOException {
        try {
            boolean hasDeadline = timeout.hasDeadline();
            long timeoutNanos = timeout.timeoutNanos();
            if (hasDeadline || timeoutNanos != 0) {
                long waitNanos;
                long start = System.nanoTime();
                if (hasDeadline && timeoutNanos != 0) {
                    waitNanos = Math.min(timeoutNanos, timeout.deadlineNanoTime() - start);
                } else if (hasDeadline) {
                    waitNanos = timeout.deadlineNanoTime() - start;
                } else {
                    waitNanos = timeoutNanos;
                }
                long elapsedNanos = 0;
                if (waitNanos > 0) {
                    long waitMillis = waitNanos / C0907C.MICROS_PER_SECOND;
                    monitor.wait(waitMillis, (int) (waitNanos - (C0907C.MICROS_PER_SECOND * waitMillis)));
                    elapsedNanos = System.nanoTime() - start;
                }
                if (elapsedNanos >= waitNanos) {
                    throw new SocketTimeoutException("timeout");
                }
                return;
            }
            monitor.wait();
        } catch (InterruptedException e) {
            throw new SocketTimeoutException("interrupted");
        }
    }
}
