package org.telegram.messenger.exoplayer2.upstream.cache;

public final class NoOpCacheEvictor implements CacheEvictor {
    public void onCacheInitialized() {
    }

    public void onStartFile(Cache cache, String key, long position, long maxLength) {
    }

    public void onSpanAdded(Cache cache, CacheSpan span) {
    }

    public void onSpanRemoved(Cache cache, CacheSpan span) {
    }

    public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
    }
}
