package org.telegram.messenger.exoplayer2.upstream.cache;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class CachedContent {
    private final TreeSet<SimpleCacheSpan> cachedSpans;
    public final int id;
    public final String key;
    private long length;

    public CachedContent(int i, String str, long j) {
        this.id = i;
        this.key = str;
        this.length = j;
        this.cachedSpans = new TreeSet();
    }

    public CachedContent(DataInputStream dataInputStream) {
        this(dataInputStream.readInt(), dataInputStream.readUTF(), dataInputStream.readLong());
    }

    public void addSpan(SimpleCacheSpan simpleCacheSpan) {
        this.cachedSpans.add(simpleCacheSpan);
    }

    public long getCachedBytes(long j, long j2) {
        SimpleCacheSpan span = getSpan(j);
        if (span.isHoleSpan()) {
            return -Math.min(span.isOpenEnded() ? Long.MAX_VALUE : span.length, j2);
        }
        long j3;
        long j4 = j + j2;
        long j5 = span.position + span.length;
        if (j5 < j4) {
            j3 = j5;
            for (SimpleCacheSpan simpleCacheSpan : this.cachedSpans.tailSet(span, false)) {
                if (simpleCacheSpan.position > j3) {
                    break;
                }
                j5 = Math.max(j3, simpleCacheSpan.length + simpleCacheSpan.position);
                if (j5 >= j4) {
                    j3 = j5;
                    break;
                }
                j3 = j5;
            }
        } else {
            j3 = j5;
        }
        return Math.min(j3 - j, j2);
    }

    public long getLength() {
        return this.length;
    }

    public SimpleCacheSpan getSpan(long j) {
        SimpleCacheSpan createLookup = SimpleCacheSpan.createLookup(this.key, j);
        SimpleCacheSpan simpleCacheSpan = (SimpleCacheSpan) this.cachedSpans.floor(createLookup);
        if (simpleCacheSpan != null && simpleCacheSpan.position + simpleCacheSpan.length > j) {
            return simpleCacheSpan;
        }
        simpleCacheSpan = (SimpleCacheSpan) this.cachedSpans.ceiling(createLookup);
        return simpleCacheSpan == null ? SimpleCacheSpan.createOpenHole(this.key, j) : SimpleCacheSpan.createClosedHole(this.key, j, simpleCacheSpan.position - j);
    }

    public TreeSet<SimpleCacheSpan> getSpans() {
        return this.cachedSpans;
    }

    public int headerHashCode() {
        return (((this.id * 31) + this.key.hashCode()) * 31) + ((int) (this.length ^ (this.length >>> 32)));
    }

    public boolean isEmpty() {
        return this.cachedSpans.isEmpty();
    }

    public boolean removeSpan(CacheSpan cacheSpan) {
        if (!this.cachedSpans.remove(cacheSpan)) {
            return false;
        }
        cacheSpan.file.delete();
        return true;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public SimpleCacheSpan touch(SimpleCacheSpan simpleCacheSpan) {
        Assertions.checkState(this.cachedSpans.remove(simpleCacheSpan));
        SimpleCacheSpan copyWithUpdatedLastAccessTime = simpleCacheSpan.copyWithUpdatedLastAccessTime(this.id);
        if (simpleCacheSpan.file.renameTo(copyWithUpdatedLastAccessTime.file)) {
            this.cachedSpans.add(copyWithUpdatedLastAccessTime);
            return copyWithUpdatedLastAccessTime;
        }
        throw new CacheException("Renaming of " + simpleCacheSpan.file + " to " + copyWithUpdatedLastAccessTime.file + " failed.");
    }

    public void writeToStream(DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.id);
        dataOutputStream.writeUTF(this.key);
        dataOutputStream.writeLong(this.length);
    }
}
