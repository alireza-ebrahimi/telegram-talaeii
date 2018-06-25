package org.telegram.messenger.exoplayer2.upstream.cache;

public interface Cache$Listener {
    void onSpanAdded(Cache cache, CacheSpan cacheSpan);

    void onSpanRemoved(Cache cache, CacheSpan cacheSpan);

    void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2);
}
