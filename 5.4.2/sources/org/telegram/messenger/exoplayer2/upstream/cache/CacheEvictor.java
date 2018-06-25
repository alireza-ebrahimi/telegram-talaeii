package org.telegram.messenger.exoplayer2.upstream.cache;

import org.telegram.messenger.exoplayer2.upstream.cache.Cache.Listener;

public interface CacheEvictor extends Listener {
    void onCacheInitialized();

    void onStartFile(Cache cache, String str, long j, long j2);
}
