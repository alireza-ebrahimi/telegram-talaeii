package org.telegram.messenger.exoplayer2.upstream.cache;

public class CacheUtil$CachingCounters {
    public volatile long alreadyCachedBytes;
    public volatile long contentLength = -1;
    public volatile long newlyCachedBytes;

    public long totalCachedBytes() {
        return this.alreadyCachedBytes + this.newlyCachedBytes;
    }
}
