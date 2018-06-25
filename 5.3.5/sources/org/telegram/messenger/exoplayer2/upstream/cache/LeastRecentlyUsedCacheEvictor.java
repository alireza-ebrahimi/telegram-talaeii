package org.telegram.messenger.exoplayer2.upstream.cache;

import java.util.Comparator;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;

public final class LeastRecentlyUsedCacheEvictor implements CacheEvictor, Comparator<CacheSpan> {
    private long currentSize;
    private final TreeSet<CacheSpan> leastRecentlyUsed = new TreeSet(this);
    private final long maxBytes;

    public LeastRecentlyUsedCacheEvictor(long maxBytes) {
        this.maxBytes = maxBytes;
    }

    public void onCacheInitialized() {
    }

    public void onStartFile(Cache cache, String key, long position, long maxLength) {
        evictCache(cache, maxLength);
    }

    public void onSpanAdded(Cache cache, CacheSpan span) {
        this.leastRecentlyUsed.add(span);
        this.currentSize += span.length;
        evictCache(cache, 0);
    }

    public void onSpanRemoved(Cache cache, CacheSpan span) {
        this.leastRecentlyUsed.remove(span);
        this.currentSize -= span.length;
    }

    public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
        onSpanRemoved(cache, oldSpan);
        onSpanAdded(cache, newSpan);
    }

    public int compare(CacheSpan lhs, CacheSpan rhs) {
        if (lhs.lastAccessTimestamp - rhs.lastAccessTimestamp == 0) {
            return lhs.compareTo(rhs);
        }
        return lhs.lastAccessTimestamp < rhs.lastAccessTimestamp ? -1 : 1;
    }

    private void evictCache(Cache cache, long requiredSpace) {
        while (this.currentSize + requiredSpace > this.maxBytes && !this.leastRecentlyUsed.isEmpty()) {
            try {
                cache.removeSpan((CacheSpan) this.leastRecentlyUsed.first());
            } catch (CacheException e) {
            }
        }
    }
}
