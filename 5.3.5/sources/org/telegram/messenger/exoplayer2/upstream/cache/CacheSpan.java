package org.telegram.messenger.exoplayer2.upstream.cache;

import android.support.annotation.NonNull;
import java.io.File;
import org.telegram.messenger.exoplayer2.C0907C;

public class CacheSpan implements Comparable<CacheSpan> {
    public final File file;
    public final boolean isCached;
    public final String key;
    public final long lastAccessTimestamp;
    public final long length;
    public final long position;

    public CacheSpan(String key, long position, long length) {
        this(key, position, length, C0907C.TIME_UNSET, null);
    }

    public CacheSpan(String key, long position, long length, long lastAccessTimestamp, File file) {
        this.key = key;
        this.position = position;
        this.length = length;
        this.isCached = file != null;
        this.file = file;
        this.lastAccessTimestamp = lastAccessTimestamp;
    }

    public boolean isOpenEnded() {
        return this.length == -1;
    }

    public boolean isHoleSpan() {
        return !this.isCached;
    }

    public int compareTo(@NonNull CacheSpan another) {
        if (!this.key.equals(another.key)) {
            return this.key.compareTo(another.key);
        }
        long startOffsetDiff = this.position - another.position;
        if (startOffsetDiff == 0) {
            return 0;
        }
        return startOffsetDiff < 0 ? -1 : 1;
    }
}
