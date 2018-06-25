package org.telegram.messenger.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.Listener;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class SimpleCache implements Cache {
    private final File cacheDir;
    private final CacheEvictor evictor;
    private final CachedContentIndex index;
    private CacheException initializationException;
    private final HashMap<String, ArrayList<Listener>> listeners;
    private final HashMap<String, CacheSpan> lockedSpans;
    private long totalSpace;

    public SimpleCache(File file, CacheEvictor cacheEvictor) {
        this(file, cacheEvictor, null, false);
    }

    SimpleCache(File file, CacheEvictor cacheEvictor, CachedContentIndex cachedContentIndex) {
        this.totalSpace = 0;
        this.cacheDir = file;
        this.evictor = cacheEvictor;
        this.lockedSpans = new HashMap();
        this.index = cachedContentIndex;
        this.listeners = new HashMap();
        final ConditionVariable conditionVariable = new ConditionVariable();
        new Thread("SimpleCache.initialize()") {
            public void run() {
                synchronized (SimpleCache.this) {
                    conditionVariable.open();
                    try {
                        SimpleCache.this.initialize();
                    } catch (CacheException e) {
                        SimpleCache.this.initializationException = e;
                    }
                    SimpleCache.this.evictor.onCacheInitialized();
                }
            }
        }.start();
        conditionVariable.block();
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, byte[] bArr) {
        this(file, cacheEvictor, bArr, bArr != null);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, byte[] bArr, boolean z) {
        this(file, cacheEvictor, new CachedContentIndex(file, bArr, z));
    }

    private void addSpan(SimpleCacheSpan simpleCacheSpan) {
        this.index.add(simpleCacheSpan.key).addSpan(simpleCacheSpan);
        this.totalSpace += simpleCacheSpan.length;
        notifySpanAdded(simpleCacheSpan);
    }

    private SimpleCacheSpan getSpan(String str, long j) {
        CachedContent cachedContent = this.index.get(str);
        if (cachedContent == null) {
            return SimpleCacheSpan.createOpenHole(str, j);
        }
        while (true) {
            SimpleCacheSpan span = cachedContent.getSpan(j);
            if (!span.isCached || span.file.exists()) {
                return span;
            }
            removeStaleSpansAndCachedContents();
        }
    }

    private void initialize() {
        if (this.cacheDir.exists()) {
            this.index.load();
            File[] listFiles = this.cacheDir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (!file.getName().equals(CachedContentIndex.FILE_NAME)) {
                        SimpleCacheSpan createCacheEntry = file.length() > 0 ? SimpleCacheSpan.createCacheEntry(file, this.index) : null;
                        if (createCacheEntry != null) {
                            addSpan(createCacheEntry);
                        } else {
                            file.delete();
                        }
                    }
                }
                this.index.removeEmpty();
                this.index.store();
                return;
            }
            return;
        }
        this.cacheDir.mkdirs();
    }

    private void notifySpanAdded(SimpleCacheSpan simpleCacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(simpleCacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanAdded(this, simpleCacheSpan);
            }
        }
        this.evictor.onSpanAdded(this, simpleCacheSpan);
    }

    private void notifySpanRemoved(CacheSpan cacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(cacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanRemoved(this, cacheSpan);
            }
        }
        this.evictor.onSpanRemoved(this, cacheSpan);
    }

    private void notifySpanTouched(SimpleCacheSpan simpleCacheSpan, CacheSpan cacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(simpleCacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanTouched(this, simpleCacheSpan, cacheSpan);
            }
        }
        this.evictor.onSpanTouched(this, simpleCacheSpan, cacheSpan);
    }

    private void removeSpan(CacheSpan cacheSpan, boolean z) {
        CachedContent cachedContent = this.index.get(cacheSpan.key);
        if (cachedContent != null && cachedContent.removeSpan(cacheSpan)) {
            this.totalSpace -= cacheSpan.length;
            if (z) {
                try {
                    if (cachedContent.isEmpty()) {
                        this.index.removeEmpty(cachedContent.key);
                        this.index.store();
                    }
                } catch (Throwable th) {
                    notifySpanRemoved(cacheSpan);
                }
            }
            notifySpanRemoved(cacheSpan);
        }
    }

    private void removeStaleSpansAndCachedContents() {
        LinkedList linkedList = new LinkedList();
        for (CachedContent spans : this.index.getAll()) {
            Iterator it = spans.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan cacheSpan = (CacheSpan) it.next();
                if (!cacheSpan.file.exists()) {
                    linkedList.add(cacheSpan);
                }
            }
        }
        Iterator it2 = linkedList.iterator();
        while (it2.hasNext()) {
            removeSpan((CacheSpan) it2.next(), false);
        }
        this.index.removeEmpty();
        this.index.store();
    }

    public synchronized NavigableSet<CacheSpan> addListener(String str, Listener listener) {
        ArrayList arrayList = (ArrayList) this.listeners.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.listeners.put(str, arrayList);
        }
        arrayList.add(listener);
        return getCachedSpans(str);
    }

    public synchronized void commitFile(File file) {
        boolean z = true;
        synchronized (this) {
            SimpleCacheSpan createCacheEntry = SimpleCacheSpan.createCacheEntry(file, this.index);
            Assertions.checkState(createCacheEntry != null);
            Assertions.checkState(this.lockedSpans.containsKey(createCacheEntry.key));
            if (file.exists()) {
                if (file.length() == 0) {
                    file.delete();
                } else {
                    Long valueOf = Long.valueOf(getContentLength(createCacheEntry.key));
                    if (valueOf.longValue() != -1) {
                        if (createCacheEntry.position + createCacheEntry.length > valueOf.longValue()) {
                            z = false;
                        }
                        Assertions.checkState(z);
                    }
                    addSpan(createCacheEntry);
                    this.index.store();
                    notifyAll();
                }
            }
        }
    }

    public synchronized long getCacheSpace() {
        return this.totalSpace;
    }

    public synchronized long getCachedBytes(String str, long j, long j2) {
        CachedContent cachedContent;
        cachedContent = this.index.get(str);
        return cachedContent != null ? cachedContent.getCachedBytes(j, j2) : -j2;
    }

    public synchronized NavigableSet<CacheSpan> getCachedSpans(String str) {
        NavigableSet<CacheSpan> treeSet;
        CachedContent cachedContent = this.index.get(str);
        treeSet = (cachedContent == null || cachedContent.isEmpty()) ? null : new TreeSet(cachedContent.getSpans());
        return treeSet;
    }

    public synchronized long getContentLength(String str) {
        return this.index.getContentLength(str);
    }

    public synchronized Set<String> getKeys() {
        return new HashSet(this.index.getKeys());
    }

    public synchronized boolean isCached(String str, long j, long j2) {
        boolean z;
        CachedContent cachedContent = this.index.get(str);
        z = cachedContent != null && cachedContent.getCachedBytes(j, j2) >= j2;
        return z;
    }

    public synchronized void releaseHoleSpan(CacheSpan cacheSpan) {
        Assertions.checkState(cacheSpan == this.lockedSpans.remove(cacheSpan.key));
        notifyAll();
    }

    public synchronized void removeListener(String str, Listener listener) {
        ArrayList arrayList = (ArrayList) this.listeners.get(str);
        if (arrayList != null) {
            arrayList.remove(listener);
            if (arrayList.isEmpty()) {
                this.listeners.remove(str);
            }
        }
    }

    public synchronized void removeSpan(CacheSpan cacheSpan) {
        removeSpan(cacheSpan, true);
    }

    public synchronized void setContentLength(String str, long j) {
        this.index.setContentLength(str, j);
        this.index.store();
    }

    public synchronized File startFile(String str, long j, long j2) {
        Assertions.checkState(this.lockedSpans.containsKey(str));
        if (!this.cacheDir.exists()) {
            removeStaleSpansAndCachedContents();
            this.cacheDir.mkdirs();
        }
        this.evictor.onStartFile(this, str, j, j2);
        return SimpleCacheSpan.getCacheFile(this.cacheDir, this.index.assignIdForKey(str), j, System.currentTimeMillis());
    }

    public synchronized SimpleCacheSpan startReadWrite(String str, long j) {
        SimpleCacheSpan startReadWriteNonBlocking;
        while (true) {
            startReadWriteNonBlocking = startReadWriteNonBlocking(str, j);
            if (startReadWriteNonBlocking == null) {
                wait();
            }
        }
        return startReadWriteNonBlocking;
    }

    public synchronized SimpleCacheSpan startReadWriteNonBlocking(String str, long j) {
        SimpleCacheSpan touch;
        if (this.initializationException != null) {
            throw this.initializationException;
        }
        SimpleCacheSpan span = getSpan(str, j);
        if (span.isCached) {
            touch = this.index.get(str).touch(span);
            notifySpanTouched(span, touch);
        } else if (this.lockedSpans.containsKey(str)) {
            touch = null;
        } else {
            this.lockedSpans.put(str, span);
            touch = span;
        }
        return touch;
    }
}
