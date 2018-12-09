package org.telegram.messenger.exoplayer2.upstream.cache;

import java.util.Comparator;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;

public final class LeastRecentlyUsedCacheEvictor implements Comparator<CacheSpan>, CacheEvictor {
    private long currentSize;
    private final TreeSet<CacheSpan> leastRecentlyUsed = new TreeSet(this);
    private final long maxBytes;

    public LeastRecentlyUsedCacheEvictor(long j) {
        this.maxBytes = j;
    }

    private void evictCache(Cache cache, long j) {
        while (this.currentSize + j > this.maxBytes && !this.leastRecentlyUsed.isEmpty()) {
            try {
                cache.removeSpan((CacheSpan) this.leastRecentlyUsed.first());
            } catch (CacheException e) {
            }
        }
    }

    public int compare(CacheSpan cacheSpan, CacheSpan cacheSpan2) {
        return cacheSpan.lastAccessTimestamp - cacheSpan2.lastAccessTimestamp == 0 ? cacheSpan.compareTo(cacheSpan2) : cacheSpan.lastAccessTimestamp < cacheSpan2.lastAccessTimestamp ? -1 : 1;
    }

    public void onCacheInitialized() {
    }

    public void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        this.leastRecentlyUsed.add(cacheSpan);
        this.currentSize += cacheSpan.length;
        evictCache(cache, 0);
    }

    public void onSpanRemoved(Cache cache, CacheSpan cacheSpan) {
        this.leastRecentlyUsed.remove(cacheSpan);
        this.currentSize -= cacheSpan.length;
    }

    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
        onSpanRemoved(cache, cacheSpan);
        onSpanAdded(cache, cacheSpan2);
    }

    public void onStartFile(Cache cache, String str, long j, long j2) {
        evictCache(cache, j2);
    }
}
