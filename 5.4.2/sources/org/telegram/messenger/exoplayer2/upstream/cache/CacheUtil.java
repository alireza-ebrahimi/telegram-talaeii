package org.telegram.messenger.exoplayer2.upstream.cache;

import android.net.Uri;
import java.io.EOFException;
import java.util.NavigableSet;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.PriorityTaskManager;

public final class CacheUtil {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;

    public static class CachingCounters {
        public volatile long alreadyCachedBytes;
        public volatile long contentLength = -1;
        public volatile long newlyCachedBytes;

        public long totalCachedBytes() {
            return this.alreadyCachedBytes + this.newlyCachedBytes;
        }
    }

    private CacheUtil() {
    }

    public static void cache(DataSpec dataSpec, Cache cache, DataSource dataSource, CachingCounters cachingCounters) {
        cache(dataSpec, cache, new CacheDataSource(cache, dataSource), new byte[131072], null, 0, cachingCounters, false);
    }

    public static void cache(DataSpec dataSpec, Cache cache, CacheDataSource cacheDataSource, byte[] bArr, PriorityTaskManager priorityTaskManager, int i, CachingCounters cachingCounters, boolean z) {
        CachingCounters cachingCounters2;
        Assertions.checkNotNull(cacheDataSource);
        Assertions.checkNotNull(bArr);
        if (cachingCounters != null) {
            getCached(dataSpec, cache, cachingCounters);
            cachingCounters2 = cachingCounters;
        } else {
            cachingCounters2 = new CachingCounters();
        }
        String key = getKey(dataSpec);
        long contentLength = dataSpec.length != -1 ? dataSpec.length : cache.getContentLength(key);
        long j = dataSpec.absoluteStreamPosition;
        while (contentLength != 0) {
            long cachedBytes = cache.getCachedBytes(key, j, contentLength != -1 ? contentLength : Long.MAX_VALUE);
            if (cachedBytes <= 0) {
                long j2 = -cachedBytes;
                if (readAndDiscard(dataSpec, j, j2, cacheDataSource, bArr, priorityTaskManager, i, cachingCounters2) >= j2) {
                    cachedBytes = j2;
                } else if (z && contentLength != -1) {
                    throw new EOFException();
                } else {
                    return;
                }
            }
            contentLength -= contentLength == -1 ? 0 : cachedBytes;
            j += cachedBytes;
        }
    }

    public static String generateKey(Uri uri) {
        return uri.toString();
    }

    public static void getCached(DataSpec dataSpec, Cache cache, CachingCounters cachingCounters) {
        String key = getKey(dataSpec);
        long j = dataSpec.absoluteStreamPosition;
        long contentLength = dataSpec.length != -1 ? dataSpec.length : cache.getContentLength(key);
        cachingCounters.contentLength = contentLength;
        cachingCounters.alreadyCachedBytes = 0;
        cachingCounters.newlyCachedBytes = 0;
        long j2 = contentLength;
        contentLength = j;
        while (j2 != 0) {
            j = cache.getCachedBytes(key, contentLength, j2 != -1 ? j2 : Long.MAX_VALUE);
            if (j > 0) {
                cachingCounters.alreadyCachedBytes += j;
            } else {
                j = -j;
                if (j == Long.MAX_VALUE) {
                    return;
                }
            }
            j2 -= j2 == -1 ? 0 : j;
            contentLength += j;
        }
    }

    public static String getKey(DataSpec dataSpec) {
        return dataSpec.key != null ? dataSpec.key : generateKey(dataSpec.uri);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long readAndDiscard(org.telegram.messenger.exoplayer2.upstream.DataSpec r15, long r16, long r18, org.telegram.messenger.exoplayer2.upstream.DataSource r20, byte[] r21, org.telegram.messenger.exoplayer2.util.PriorityTaskManager r22, int r23, org.telegram.messenger.exoplayer2.upstream.cache.CacheUtil.CachingCounters r24) {
        /*
        r2 = r15;
    L_0x0001:
        if (r22 == 0) goto L_0x0006;
    L_0x0003:
        r22.proceed(r23);
    L_0x0006:
        r3 = java.lang.Thread.interrupted();	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        if (r3 == 0) goto L_0x0019;
    L_0x000c:
        r3 = new java.lang.InterruptedException;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r3.<init>();	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        throw r3;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
    L_0x0012:
        r3 = move-exception;
        r3 = r2;
    L_0x0014:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r20);
        r2 = r3;
        goto L_0x0001;
    L_0x0019:
        r3 = new org.telegram.messenger.exoplayer2.upstream.DataSpec;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r4 = r2.uri;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r5 = r2.postBody;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r6 = r2.position;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r6 = r6 + r16;
        r8 = r2.absoluteStreamPosition;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r8 = r6 - r8;
        r10 = -1;
        r12 = r2.key;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r6 = r2.flags;	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r13 = r6 | 2;
        r6 = r16;
        r3.<init>(r4, r5, r6, r8, r10, r12, r13);	 Catch:{ PriorityTooLowException -> 0x0012, all -> 0x00a8 }
        r0 = r20;
        r4 = r0.open(r3);	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r0 = r24;
        r6 = r0.contentLength;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r8 = -1;
        r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r2 != 0) goto L_0x0051;
    L_0x0044:
        r6 = -1;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x0051;
    L_0x004a:
        r6 = r3.absoluteStreamPosition;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r4 = r4 + r6;
        r0 = r24;
        r0.contentLength = r4;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
    L_0x0051:
        r4 = 0;
    L_0x0053:
        r2 = (r4 > r18 ? 1 : (r4 == r18 ? 0 : -1));
        if (r2 == 0) goto L_0x0093;
    L_0x0057:
        r2 = java.lang.Thread.interrupted();	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        if (r2 == 0) goto L_0x0065;
    L_0x005d:
        r2 = new java.lang.InterruptedException;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r2.<init>();	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        throw r2;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
    L_0x0063:
        r2 = move-exception;
        goto L_0x0014;
    L_0x0065:
        r6 = 0;
        r8 = -1;
        r2 = (r18 > r8 ? 1 : (r18 == r8 ? 0 : -1));
        if (r2 == 0) goto L_0x0097;
    L_0x006c:
        r0 = r21;
        r2 = r0.length;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r8 = (long) r2;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r10 = r18 - r4;
        r8 = java.lang.Math.min(r8, r10);	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r2 = (int) r8;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
    L_0x0077:
        r0 = r20;
        r1 = r21;
        r2 = r0.read(r1, r6, r2);	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r6 = -1;
        if (r2 != r6) goto L_0x009b;
    L_0x0082:
        r0 = r24;
        r6 = r0.contentLength;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r8 = -1;
        r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r2 != 0) goto L_0x0093;
    L_0x008c:
        r6 = r3.absoluteStreamPosition;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r6 = r6 + r4;
        r0 = r24;
        r0.contentLength = r6;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
    L_0x0093:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r20);
        return r4;
    L_0x0097:
        r0 = r21;
        r2 = r0.length;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        goto L_0x0077;
    L_0x009b:
        r6 = (long) r2;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r4 = r4 + r6;
        r0 = r24;
        r6 = r0.newlyCachedBytes;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r8 = (long) r2;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        r6 = r6 + r8;
        r0 = r24;
        r0.newlyCachedBytes = r6;	 Catch:{ PriorityTooLowException -> 0x0063, all -> 0x00a8 }
        goto L_0x0053;
    L_0x00a8:
        r2 = move-exception;
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r20);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.cache.CacheUtil.readAndDiscard(org.telegram.messenger.exoplayer2.upstream.DataSpec, long, long, org.telegram.messenger.exoplayer2.upstream.DataSource, byte[], org.telegram.messenger.exoplayer2.util.PriorityTaskManager, int, org.telegram.messenger.exoplayer2.upstream.cache.CacheUtil$CachingCounters):long");
    }

    public static void remove(Cache cache, String str) {
        NavigableSet<CacheSpan> cachedSpans = cache.getCachedSpans(str);
        if (cachedSpans != null) {
            for (CacheSpan removeSpan : cachedSpans) {
                try {
                    cache.removeSpan(removeSpan);
                } catch (CacheException e) {
                }
            }
        }
    }
}
