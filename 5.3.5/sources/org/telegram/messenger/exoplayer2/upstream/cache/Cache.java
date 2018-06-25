package org.telegram.messenger.exoplayer2.upstream.cache;

import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.Set;

public interface Cache {

    public static class CacheException extends IOException {
        public CacheException(String message) {
            super(message);
        }

        public CacheException(IOException cause) {
            super(cause);
        }
    }

    NavigableSet<CacheSpan> addListener(String str, Cache$Listener cache$Listener);

    void commitFile(File file) throws CacheException;

    long getCacheSpace();

    long getCachedBytes(String str, long j, long j2);

    NavigableSet<CacheSpan> getCachedSpans(String str);

    long getContentLength(String str);

    Set<String> getKeys();

    boolean isCached(String str, long j, long j2);

    void releaseHoleSpan(CacheSpan cacheSpan);

    void removeListener(String str, Cache$Listener cache$Listener);

    void removeSpan(CacheSpan cacheSpan) throws CacheException;

    void setContentLength(String str, long j) throws CacheException;

    File startFile(String str, long j, long j2) throws CacheException;

    CacheSpan startReadWrite(String str, long j) throws InterruptedException, CacheException;

    CacheSpan startReadWriteNonBlocking(String str, long j) throws CacheException;
}
