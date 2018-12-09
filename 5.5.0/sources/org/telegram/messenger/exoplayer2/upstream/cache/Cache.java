package org.telegram.messenger.exoplayer2.upstream.cache;

import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.Set;

public interface Cache {

    public static class CacheException extends IOException {
        public CacheException(IOException iOException) {
            super(iOException);
        }

        public CacheException(String str) {
            super(str);
        }
    }

    public interface Listener {
        void onSpanAdded(Cache cache, CacheSpan cacheSpan);

        void onSpanRemoved(Cache cache, CacheSpan cacheSpan);

        void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2);
    }

    NavigableSet<CacheSpan> addListener(String str, Listener listener);

    void commitFile(File file);

    long getCacheSpace();

    long getCachedBytes(String str, long j, long j2);

    NavigableSet<CacheSpan> getCachedSpans(String str);

    long getContentLength(String str);

    Set<String> getKeys();

    boolean isCached(String str, long j, long j2);

    void releaseHoleSpan(CacheSpan cacheSpan);

    void removeListener(String str, Listener listener);

    void removeSpan(CacheSpan cacheSpan);

    void setContentLength(String str, long j);

    File startFile(String str, long j, long j2);

    CacheSpan startReadWrite(String str, long j);

    CacheSpan startReadWriteNonBlocking(String str, long j);
}
