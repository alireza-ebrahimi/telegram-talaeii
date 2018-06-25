package org.telegram.messenger.exoplayer2.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

public final class ReusableBufferedOutputStream extends BufferedOutputStream {
    private boolean closed;

    public ReusableBufferedOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public ReusableBufferedOutputStream(OutputStream outputStream, int i) {
        super(outputStream, i);
    }

    public void close() {
        Throwable th;
        this.closed = true;
        Throwable th2 = null;
        try {
            flush();
        } catch (Throwable th3) {
            th2 = th3;
        }
        try {
            this.out.close();
            th = th2;
        } catch (Throwable th4) {
            th = th4;
            if (th2 != null) {
                th = th2;
            }
        }
        if (th != null) {
            Util.sneakyThrow(th);
        }
    }

    public void reset(OutputStream outputStream) {
        Assertions.checkState(this.closed);
        this.out = outputStream;
        this.count = 0;
        this.closed = false;
    }
}
