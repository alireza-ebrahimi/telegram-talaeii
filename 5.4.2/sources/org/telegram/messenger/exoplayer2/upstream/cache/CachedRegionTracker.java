package org.telegram.messenger.exoplayer2.upstream.cache;

import android.util.Log;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.extractor.ChunkIndex;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.Listener;

public final class CachedRegionTracker implements Listener {
    public static final int CACHED_TO_END = -2;
    public static final int NOT_CACHED = -1;
    private static final String TAG = "CachedRegionTracker";
    private final Cache cache;
    private final String cacheKey;
    private final ChunkIndex chunkIndex;
    private final Region lookupRegion = new Region(0, 0);
    private final TreeSet<Region> regions = new TreeSet();

    private static class Region implements Comparable<Region> {
        public long endOffset;
        public int endOffsetIndex;
        public long startOffset;

        public Region(long j, long j2) {
            this.startOffset = j;
            this.endOffset = j2;
        }

        public int compareTo(Region region) {
            return this.startOffset < region.startOffset ? -1 : this.startOffset == region.startOffset ? 0 : 1;
        }
    }

    public CachedRegionTracker(Cache cache, String str, ChunkIndex chunkIndex) {
        this.cache = cache;
        this.cacheKey = str;
        this.chunkIndex = chunkIndex;
        synchronized (this) {
            NavigableSet addListener = cache.addListener(str, this);
            if (addListener != null) {
                Iterator descendingIterator = addListener.descendingIterator();
                while (descendingIterator.hasNext()) {
                    mergeSpan((CacheSpan) descendingIterator.next());
                }
            }
        }
    }

    private void mergeSpan(CacheSpan cacheSpan) {
        Region region = new Region(cacheSpan.position, cacheSpan.position + cacheSpan.length);
        Region region2 = (Region) this.regions.floor(region);
        Region region3 = (Region) this.regions.ceiling(region);
        boolean regionsConnect = regionsConnect(region2, region);
        if (regionsConnect(region, region3)) {
            if (regionsConnect) {
                region2.endOffset = region3.endOffset;
                region2.endOffsetIndex = region3.endOffsetIndex;
            } else {
                region.endOffset = region3.endOffset;
                region.endOffsetIndex = region3.endOffsetIndex;
                this.regions.add(region);
            }
            this.regions.remove(region3);
        } else if (regionsConnect) {
            region2.endOffset = region.endOffset;
            int i = region2.endOffsetIndex;
            while (i < this.chunkIndex.length - 1 && this.chunkIndex.offsets[i + 1] <= region2.endOffset) {
                i++;
            }
            region2.endOffsetIndex = i;
        } else {
            int binarySearch = Arrays.binarySearch(this.chunkIndex.offsets, region.endOffset);
            if (binarySearch < 0) {
                binarySearch = (-binarySearch) - 2;
            }
            region.endOffsetIndex = binarySearch;
            this.regions.add(region);
        }
    }

    private boolean regionsConnect(Region region, Region region2) {
        return (region == null || region2 == null || region.endOffset != region2.startOffset) ? false : true;
    }

    public synchronized int getRegionEndTimeMs(long j) {
        int i;
        this.lookupRegion.startOffset = j;
        Region region = (Region) this.regions.floor(this.lookupRegion);
        if (region == null || j > region.endOffset || region.endOffsetIndex == -1) {
            i = -1;
        } else {
            int i2 = region.endOffsetIndex;
            if (i2 == this.chunkIndex.length - 1 && region.endOffset == this.chunkIndex.offsets[i2] + ((long) this.chunkIndex.sizes[i2])) {
                i = -2;
            } else {
                i = (int) ((this.chunkIndex.timesUs[i2] + ((this.chunkIndex.durationsUs[i2] * (region.endOffset - this.chunkIndex.offsets[i2])) / ((long) this.chunkIndex.sizes[i2]))) / 1000);
            }
        }
        return i;
    }

    public synchronized void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        mergeSpan(cacheSpan);
    }

    public synchronized void onSpanRemoved(Cache cache, CacheSpan cacheSpan) {
        Region region = new Region(cacheSpan.position, cacheSpan.position + cacheSpan.length);
        Region region2 = (Region) this.regions.floor(region);
        if (region2 == null) {
            Log.e(TAG, "Removed a span we were not aware of");
        } else {
            this.regions.remove(region2);
            if (region2.startOffset < region.startOffset) {
                Region region3 = new Region(region2.startOffset, region.startOffset);
                int binarySearch = Arrays.binarySearch(this.chunkIndex.offsets, region3.endOffset);
                if (binarySearch < 0) {
                    binarySearch = (-binarySearch) - 2;
                }
                region3.endOffsetIndex = binarySearch;
                this.regions.add(region3);
            }
            if (region2.endOffset > region.endOffset) {
                Region region4 = new Region(region.endOffset + 1, region2.endOffset);
                region4.endOffsetIndex = region2.endOffsetIndex;
                this.regions.add(region4);
            }
        }
    }

    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
    }

    public void release() {
        this.cache.removeListener(this.cacheKey, this);
    }
}
