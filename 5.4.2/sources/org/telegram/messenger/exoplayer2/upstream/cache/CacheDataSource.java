package org.telegram.messenger.exoplayer2.upstream.cache;

import android.net.Uri;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.telegram.messenger.exoplayer2.upstream.DataSink;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSourceException;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.FileDataSource;
import org.telegram.messenger.exoplayer2.upstream.TeeDataSource;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;

public final class CacheDataSource implements DataSource {
    public static final long DEFAULT_MAX_CACHE_FILE_SIZE = 2097152;
    public static final int FLAG_BLOCK_ON_CACHE = 1;
    public static final int FLAG_IGNORE_CACHE_FOR_UNSET_LENGTH_REQUESTS = 4;
    public static final int FLAG_IGNORE_CACHE_ON_ERROR = 2;
    private final boolean blockOnCache;
    private long bytesRemaining;
    private final Cache cache;
    private final DataSource cacheReadDataSource;
    private final DataSource cacheWriteDataSource;
    private DataSource currentDataSource;
    private boolean currentRequestIgnoresCache;
    private boolean currentRequestUnbounded;
    private final EventListener eventListener;
    private int flags;
    private final boolean ignoreCacheForUnsetLengthRequests;
    private final boolean ignoreCacheOnError;
    private String key;
    private CacheSpan lockedSpan;
    private long readPosition;
    private boolean seenCacheError;
    private long totalCachedBytesRead;
    private final DataSource upstreamDataSource;
    private Uri uri;

    public interface EventListener {
        void onCachedBytesRead(long j, long j2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public CacheDataSource(Cache cache, DataSource dataSource) {
        this(cache, dataSource, 0, DEFAULT_MAX_CACHE_FILE_SIZE);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, int i) {
        this(cache, dataSource, i, DEFAULT_MAX_CACHE_FILE_SIZE);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, int i, long j) {
        this(cache, dataSource, new FileDataSource(), new CacheDataSink(cache, j), i, null);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, DataSource dataSource2, DataSink dataSink, int i, EventListener eventListener) {
        boolean z = true;
        this.cache = cache;
        this.cacheReadDataSource = dataSource2;
        this.blockOnCache = (i & 1) != 0;
        this.ignoreCacheOnError = (i & 2) != 0;
        if ((i & 4) == 0) {
            z = false;
        }
        this.ignoreCacheForUnsetLengthRequests = z;
        this.upstreamDataSource = dataSource;
        if (dataSink != null) {
            this.cacheWriteDataSource = new TeeDataSource(dataSource, dataSink);
        } else {
            this.cacheWriteDataSource = null;
        }
        this.eventListener = eventListener;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void closeCurrentSource() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0005 in list [B:6:0x0015]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r4 = this;
        r3 = 0;
        r0 = r4.currentDataSource;
        if (r0 != 0) goto L_0x0006;
    L_0x0005:
        return;
    L_0x0006:
        r0 = r4.currentDataSource;	 Catch:{ all -> 0x001f }
        r0.close();	 Catch:{ all -> 0x001f }
        r0 = 0;	 Catch:{ all -> 0x001f }
        r4.currentDataSource = r0;	 Catch:{ all -> 0x001f }
        r0 = 0;	 Catch:{ all -> 0x001f }
        r4.currentRequestUnbounded = r0;	 Catch:{ all -> 0x001f }
        r0 = r4.lockedSpan;
        if (r0 == 0) goto L_0x0005;
    L_0x0015:
        r0 = r4.cache;
        r1 = r4.lockedSpan;
        r0.releaseHoleSpan(r1);
        r4.lockedSpan = r3;
        goto L_0x0005;
    L_0x001f:
        r0 = move-exception;
        r1 = r4.lockedSpan;
        if (r1 == 0) goto L_0x002d;
    L_0x0024:
        r1 = r4.cache;
        r2 = r4.lockedSpan;
        r1.releaseHoleSpan(r2);
        r4.lockedSpan = r3;
    L_0x002d:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSource.closeCurrentSource():void");
    }

    private void handleBeforeThrow(IOException iOException) {
        if (this.currentDataSource == this.cacheReadDataSource || (iOException instanceof CacheException)) {
            this.seenCacheError = true;
        }
    }

    private void notifyBytesRead() {
        if (this.eventListener != null && this.totalCachedBytesRead > 0) {
            this.eventListener.onCachedBytesRead(this.cache.getCacheSpace(), this.totalCachedBytesRead);
            this.totalCachedBytesRead = 0;
        }
    }

    private boolean openNextSource(boolean z) {
        CacheSpan cacheSpan;
        DataSpec dataSpec;
        long open;
        if (this.currentRequestIgnoresCache) {
            cacheSpan = null;
        } else if (this.blockOnCache) {
            try {
                cacheSpan = this.cache.startReadWrite(this.key, this.readPosition);
            } catch (InterruptedException e) {
                throw new InterruptedIOException();
            }
        } else {
            cacheSpan = this.cache.startReadWriteNonBlocking(this.key, this.readPosition);
        }
        if (cacheSpan == null) {
            this.currentDataSource = this.upstreamDataSource;
            dataSpec = new DataSpec(this.uri, this.readPosition, this.bytesRemaining, this.key, this.flags);
        } else if (cacheSpan.isCached) {
            Uri fromFile = Uri.fromFile(cacheSpan.file);
            r4 = this.readPosition - cacheSpan.position;
            long j = cacheSpan.length - r4;
            if (this.bytesRemaining != -1) {
                j = Math.min(j, this.bytesRemaining);
            }
            r0 = new DataSpec(fromFile, this.readPosition, r4, j, this.key, this.flags);
            this.currentDataSource = this.cacheReadDataSource;
            dataSpec = r0;
        } else {
            if (cacheSpan.isOpenEnded()) {
                r4 = this.bytesRemaining;
            } else {
                r4 = cacheSpan.length;
                if (this.bytesRemaining != -1) {
                    r4 = Math.min(r4, this.bytesRemaining);
                }
            }
            r0 = new DataSpec(this.uri, this.readPosition, r4, this.key, this.flags);
            if (this.cacheWriteDataSource != null) {
                this.currentDataSource = this.cacheWriteDataSource;
                this.lockedSpan = cacheSpan;
                dataSpec = r0;
            } else {
                this.currentDataSource = this.upstreamDataSource;
                this.cache.releaseHoleSpan(cacheSpan);
                dataSpec = r0;
            }
        }
        this.currentRequestUnbounded = dataSpec.length == -1;
        boolean z2 = false;
        try {
            open = this.currentDataSource.open(dataSpec);
            z2 = true;
        } catch (Throwable e2) {
            Throwable th = e2;
            if (!z && this.currentRequestUnbounded) {
                Throwable th2 = th;
                while (th2 != null) {
                    if ((th2 instanceof DataSourceException) && ((DataSourceException) th2).reason == 0) {
                        th = null;
                        break;
                    }
                    th2 = th2.getCause();
                }
            }
            if (th != null) {
                throw th;
            }
            open = 0;
        }
        if (this.currentRequestUnbounded && open != -1) {
            this.bytesRemaining = open;
            setContentLength(dataSpec.position + this.bytesRemaining);
        }
        return z2;
    }

    private void setContentLength(long j) {
        if (this.currentDataSource == this.cacheWriteDataSource) {
            this.cache.setContentLength(this.key, j);
        }
    }

    public void close() {
        this.uri = null;
        notifyBytesRead();
        try {
            closeCurrentSource();
        } catch (IOException e) {
            handleBeforeThrow(e);
            throw e;
        }
    }

    public Uri getUri() {
        return this.currentDataSource == this.upstreamDataSource ? this.currentDataSource.getUri() : this.uri;
    }

    public long open(DataSpec dataSpec) {
        boolean z = true;
        try {
            this.uri = dataSpec.uri;
            this.flags = dataSpec.flags;
            this.key = CacheUtil.getKey(dataSpec);
            this.readPosition = dataSpec.position;
            if (!((this.ignoreCacheOnError && this.seenCacheError) || (dataSpec.length == -1 && this.ignoreCacheForUnsetLengthRequests))) {
                z = false;
            }
            this.currentRequestIgnoresCache = z;
            if (dataSpec.length != -1 || this.currentRequestIgnoresCache) {
                this.bytesRemaining = dataSpec.length;
            } else {
                this.bytesRemaining = this.cache.getContentLength(this.key);
                if (this.bytesRemaining != -1) {
                    this.bytesRemaining -= dataSpec.position;
                    if (this.bytesRemaining <= 0) {
                        throw new DataSourceException(0);
                    }
                }
            }
            openNextSource(true);
            return this.bytesRemaining;
        } catch (IOException e) {
            handleBeforeThrow(e);
            throw e;
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            int read = this.currentDataSource.read(bArr, i, i2);
            if (read >= 0) {
                if (this.currentDataSource == this.cacheReadDataSource) {
                    this.totalCachedBytesRead += (long) read;
                }
                this.readPosition += (long) read;
                if (this.bytesRemaining == -1) {
                    return read;
                }
                this.bytesRemaining -= (long) read;
                return read;
            }
            if (this.currentRequestUnbounded) {
                setContentLength(this.readPosition);
                this.bytesRemaining = 0;
            }
            closeCurrentSource();
            return ((this.bytesRemaining > 0 || this.bytesRemaining == -1) && openNextSource(false)) ? read(bArr, i, i2) : read;
        } catch (IOException e) {
            handleBeforeThrow(e);
            throw e;
        }
    }
}
