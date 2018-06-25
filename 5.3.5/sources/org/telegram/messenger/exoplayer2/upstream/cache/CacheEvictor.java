package org.telegram.messenger.exoplayer2.upstream.cache;

public interface CacheEvictor extends Cache$Listener {
    void onCacheInitialized();

    void onStartFile(Cache cache, String str, long j, long j2);
}
