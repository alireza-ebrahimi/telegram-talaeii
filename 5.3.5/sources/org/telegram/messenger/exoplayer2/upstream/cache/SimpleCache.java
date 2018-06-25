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
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class SimpleCache implements Cache {
    private final File cacheDir;
    private final CacheEvictor evictor;
    private final CachedContentIndex index;
    private CacheException initializationException;
    private final HashMap<String, ArrayList<Cache$Listener>> listeners;
    private final HashMap<String, CacheSpan> lockedSpans;
    private long totalSpace;

    public SimpleCache(File cacheDir, CacheEvictor evictor) {
        this(cacheDir, evictor, null, false);
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor, byte[] secretKey) {
        this(cacheDir, evictor, secretKey, secretKey != null);
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor, byte[] secretKey, boolean encrypt) {
        this(cacheDir, evictor, new CachedContentIndex(cacheDir, secretKey, encrypt));
    }

    SimpleCache(File cacheDir, CacheEvictor evictor, CachedContentIndex index) {
        this.totalSpace = 0;
        this.cacheDir = cacheDir;
        this.evictor = evictor;
        this.lockedSpans = new HashMap();
        this.index = index;
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

    public synchronized NavigableSet<CacheSpan> addListener(String key, Cache$Listener listener) {
        ArrayList<Cache$Listener> listenersForKey = (ArrayList) this.listeners.get(key);
        if (listenersForKey == null) {
            listenersForKey = new ArrayList();
            this.listeners.put(key, listenersForKey);
        }
        listenersForKey.add(listener);
        return getCachedSpans(key);
    }

    public synchronized void removeListener(String key, Cache$Listener listener) {
        ArrayList<Cache$Listener> listenersForKey = (ArrayList) this.listeners.get(key);
        if (listenersForKey != null) {
            listenersForKey.remove(listener);
            if (listenersForKey.isEmpty()) {
                this.listeners.remove(key);
            }
        }
    }

    public synchronized NavigableSet<CacheSpan> getCachedSpans(String key) {
        NavigableSet<CacheSpan> navigableSet;
        CachedContent cachedContent = this.index.get(key);
        if (cachedContent == null || cachedContent.isEmpty()) {
            navigableSet = null;
        } else {
            navigableSet = new TreeSet(cachedContent.getSpans());
        }
        return navigableSet;
    }

    public synchronized Set<String> getKeys() {
        return new HashSet(this.index.getKeys());
    }

    public synchronized long getCacheSpace() {
        return this.totalSpace;
    }

    public synchronized SimpleCacheSpan startReadWrite(String key, long position) throws InterruptedException, CacheException {
        SimpleCacheSpan span;
        while (true) {
            span = startReadWriteNonBlocking(key, position);
            if (span == null) {
                wait();
            }
        }
        return span;
    }

    public synchronized SimpleCacheSpan startReadWriteNonBlocking(String key, long position) throws CacheException {
        SimpleCacheSpan newCacheSpan;
        if (this.initializationException != null) {
            throw this.initializationException;
        }
        SimpleCacheSpan cacheSpan = getSpan(key, position);
        if (cacheSpan.isCached) {
            newCacheSpan = this.index.get(key).touch(cacheSpan);
            notifySpanTouched(cacheSpan, newCacheSpan);
        } else if (this.lockedSpans.containsKey(key)) {
            newCacheSpan = null;
        } else {
            this.lockedSpans.put(key, cacheSpan);
            newCacheSpan = cacheSpan;
        }
        return newCacheSpan;
    }

    public synchronized File startFile(String key, long position, long maxLength) throws CacheException {
        Assertions.checkState(this.lockedSpans.containsKey(key));
        if (!this.cacheDir.exists()) {
            removeStaleSpansAndCachedContents();
            this.cacheDir.mkdirs();
        }
        this.evictor.onStartFile(this, key, position, maxLength);
        return SimpleCacheSpan.getCacheFile(this.cacheDir, this.index.assignIdForKey(key), position, System.currentTimeMillis());
    }

    public synchronized void commitFile(File file) throws CacheException {
        boolean z = true;
        synchronized (this) {
            SimpleCacheSpan span = SimpleCacheSpan.createCacheEntry(file, this.index);
            Assertions.checkState(span != null);
            Assertions.checkState(this.lockedSpans.containsKey(span.key));
            if (file.exists()) {
                if (file.length() == 0) {
                    file.delete();
                } else {
                    Long length = Long.valueOf(getContentLength(span.key));
                    if (length.longValue() != -1) {
                        if (span.position + span.length > length.longValue()) {
                            z = false;
                        }
                        Assertions.checkState(z);
                    }
                    addSpan(span);
                    this.index.store();
                    notifyAll();
                }
            }
        }
    }

    public synchronized void releaseHoleSpan(CacheSpan holeSpan) {
        Assertions.checkState(holeSpan == this.lockedSpans.remove(holeSpan.key));
        notifyAll();
    }

    private SimpleCacheSpan getSpan(String key, long position) throws CacheException {
        CachedContent cachedContent = this.index.get(key);
        if (cachedContent == null) {
            return SimpleCacheSpan.createOpenHole(key, position);
        }
        while (true) {
            SimpleCacheSpan span = cachedContent.getSpan(position);
            if (!span.isCached || span.file.exists()) {
                return span;
            }
            removeStaleSpansAndCachedContents();
        }
    }

    private void initialize() throws CacheException {
        if (this.cacheDir.exists()) {
            this.index.load();
            File[] files = this.cacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.getName().equals(CachedContentIndex.FILE_NAME)) {
                        SimpleCacheSpan span = file.length() > 0 ? SimpleCacheSpan.createCacheEntry(file, this.index) : null;
                        if (span != null) {
                            addSpan(span);
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

    private void addSpan(SimpleCacheSpan span) {
        this.index.add(span.key).addSpan(span);
        this.totalSpace += span.length;
        notifySpanAdded(span);
    }

    private void removeSpan(CacheSpan span, boolean removeEmptyCachedContent) throws CacheException {
        CachedContent cachedContent = this.index.get(span.key);
        if (cachedContent != null && cachedContent.removeSpan(span)) {
            this.totalSpace -= span.length;
            if (removeEmptyCachedContent) {
                try {
                    if (cachedContent.isEmpty()) {
                        this.index.removeEmpty(cachedContent.key);
                        this.index.store();
                    }
                } catch (Throwable th) {
                    notifySpanRemoved(span);
                }
            }
            notifySpanRemoved(span);
        }
    }

    public synchronized void removeSpan(CacheSpan span) throws CacheException {
        removeSpan(span, true);
    }

    private void removeStaleSpansAndCachedContents() throws CacheException {
        LinkedList<CacheSpan> spansToBeRemoved = new LinkedList();
        for (CachedContent cachedContent : this.index.getAll()) {
            Iterator it = cachedContent.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan span = (CacheSpan) it.next();
                if (!span.file.exists()) {
                    spansToBeRemoved.add(span);
                }
            }
        }
        Iterator it2 = spansToBeRemoved.iterator();
        while (it2.hasNext()) {
            removeSpan((CacheSpan) it2.next(), false);
        }
        this.index.removeEmpty();
        this.index.store();
    }

    private void notifySpanRemoved(CacheSpan span) {
        ArrayList<Cache$Listener> keyListeners = (ArrayList) this.listeners.get(span.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                ((Cache$Listener) keyListeners.get(i)).onSpanRemoved(this, span);
            }
        }
        this.evictor.onSpanRemoved(this, span);
    }

    private void notifySpanAdded(SimpleCacheSpan span) {
        ArrayList<Cache$Listener> keyListeners = (ArrayList) this.listeners.get(span.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                ((Cache$Listener) keyListeners.get(i)).onSpanAdded(this, span);
            }
        }
        this.evictor.onSpanAdded(this, span);
    }

    private void notifySpanTouched(SimpleCacheSpan oldSpan, CacheSpan newSpan) {
        ArrayList<Cache$Listener> keyListeners = (ArrayList) this.listeners.get(oldSpan.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                ((Cache$Listener) keyListeners.get(i)).onSpanTouched(this, oldSpan, newSpan);
            }
        }
        this.evictor.onSpanTouched(this, oldSpan, newSpan);
    }

    public synchronized boolean isCached(String key, long position, long length) {
        boolean z;
        CachedContent cachedContent = this.index.get(key);
        z = cachedContent != null && cachedContent.getCachedBytes(position, length) >= length;
        return z;
    }

    public synchronized long getCachedBytes(String key, long position, long length) {
        CachedContent cachedContent;
        cachedContent = this.index.get(key);
        return cachedContent != null ? cachedContent.getCachedBytes(position, length) : -length;
    }

    public synchronized void setContentLength(String key, long length) throws CacheException {
        this.index.setContentLength(key, length);
        this.index.store();
    }

    public synchronized long getContentLength(String key) {
        return this.index.getContentLength(key);
    }
}
