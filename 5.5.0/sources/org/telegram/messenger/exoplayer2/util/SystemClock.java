package org.telegram.messenger.exoplayer2.util;

final class SystemClock implements Clock {
    SystemClock() {
    }

    public long elapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    public void sleep(long j) {
        android.os.SystemClock.sleep(j);
    }
}
