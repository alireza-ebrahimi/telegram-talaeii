package org.telegram.messenger.exoplayer2;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class ExoPlaybackException extends Exception {
    public static final int TYPE_RENDERER = 1;
    public static final int TYPE_SOURCE = 0;
    public static final int TYPE_UNEXPECTED = 2;
    public final int rendererIndex;
    public final int type;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private ExoPlaybackException(int i, String str, Throwable th, int i2) {
        super(str, th);
        this.type = i;
        this.rendererIndex = i2;
    }

    public static ExoPlaybackException createForRenderer(Exception exception, int i) {
        return new ExoPlaybackException(1, null, exception, i);
    }

    public static ExoPlaybackException createForSource(IOException iOException) {
        return new ExoPlaybackException(0, null, iOException, -1);
    }

    static ExoPlaybackException createForUnexpected(RuntimeException runtimeException) {
        return new ExoPlaybackException(2, null, runtimeException, -1);
    }

    public Exception getRendererException() {
        boolean z = true;
        if (this.type != 1) {
            z = false;
        }
        Assertions.checkState(z);
        return (Exception) getCause();
    }

    public IOException getSourceException() {
        Assertions.checkState(this.type == 0);
        return (IOException) getCause();
    }

    public RuntimeException getUnexpectedException() {
        Assertions.checkState(this.type == 2);
        return (RuntimeException) getCause();
    }
}
