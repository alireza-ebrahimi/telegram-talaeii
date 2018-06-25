package org.telegram.messenger.exoplayer2.upstream.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.telegram.messenger.exoplayer2.upstream.DataSink;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ReusableBufferedOutputStream;

public final class CacheDataSink implements DataSink {
    public static final int DEFAULT_BUFFER_SIZE = 20480;
    private final int bufferSize;
    private ReusableBufferedOutputStream bufferedOutputStream;
    private final Cache cache;
    private DataSpec dataSpec;
    private long dataSpecBytesWritten;
    private File file;
    private final long maxCacheFileSize;
    private OutputStream outputStream;
    private long outputStreamBytesWritten;
    private FileOutputStream underlyingFileOutputStream;

    public CacheDataSink(Cache cache, long maxCacheFileSize) {
        this(cache, maxCacheFileSize, DEFAULT_BUFFER_SIZE);
    }

    public CacheDataSink(Cache cache, long maxCacheFileSize, int bufferSize) {
        this.cache = (Cache) Assertions.checkNotNull(cache);
        this.maxCacheFileSize = maxCacheFileSize;
        this.bufferSize = bufferSize;
    }

    public void open(DataSpec dataSpec) throws CacheDataSink$CacheDataSinkException {
        if (dataSpec.length != -1 || dataSpec.isFlagSet(2)) {
            this.dataSpec = dataSpec;
            this.dataSpecBytesWritten = 0;
            try {
                openNextOutputStream();
                return;
            } catch (IOException e) {
                throw new CacheDataSink$CacheDataSinkException(e);
            }
        }
        this.dataSpec = null;
    }

    public void write(byte[] buffer, int offset, int length) throws CacheDataSink$CacheDataSinkException {
        if (this.dataSpec != null) {
            int bytesWritten = 0;
            while (bytesWritten < length) {
                try {
                    if (this.outputStreamBytesWritten == this.maxCacheFileSize) {
                        closeCurrentOutputStream();
                        openNextOutputStream();
                    }
                    int bytesToWrite = (int) Math.min((long) (length - bytesWritten), this.maxCacheFileSize - this.outputStreamBytesWritten);
                    this.outputStream.write(buffer, offset + bytesWritten, bytesToWrite);
                    bytesWritten += bytesToWrite;
                    this.outputStreamBytesWritten += (long) bytesToWrite;
                    this.dataSpecBytesWritten += (long) bytesToWrite;
                } catch (IOException e) {
                    throw new CacheDataSink$CacheDataSinkException(e);
                }
            }
        }
    }

    public void close() throws CacheDataSink$CacheDataSinkException {
        if (this.dataSpec != null) {
            try {
                closeCurrentOutputStream();
            } catch (IOException e) {
                throw new CacheDataSink$CacheDataSinkException(e);
            }
        }
    }

    private void openNextOutputStream() throws IOException {
        long maxLength;
        if (this.dataSpec.length == -1) {
            maxLength = this.maxCacheFileSize;
        } else {
            maxLength = Math.min(this.dataSpec.length - this.dataSpecBytesWritten, this.maxCacheFileSize);
        }
        this.file = this.cache.startFile(this.dataSpec.key, this.dataSpec.absoluteStreamPosition + this.dataSpecBytesWritten, maxLength);
        this.underlyingFileOutputStream = new FileOutputStream(this.file);
        if (this.bufferSize > 0) {
            if (this.bufferedOutputStream == null) {
                this.bufferedOutputStream = new ReusableBufferedOutputStream(this.underlyingFileOutputStream, this.bufferSize);
            } else {
                this.bufferedOutputStream.reset(this.underlyingFileOutputStream);
            }
            this.outputStream = this.bufferedOutputStream;
        } else {
            this.outputStream = this.underlyingFileOutputStream;
        }
        this.outputStreamBytesWritten = 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void closeCurrentOutputStream() throws java.io.IOException {
        /*
        r5 = this;
        r4 = 0;
        r2 = r5.outputStream;
        if (r2 != 0) goto L_0x0006;
    L_0x0005:
        return;
    L_0x0006:
        r1 = 0;
        r2 = r5.outputStream;	 Catch:{ all -> 0x002d }
        r2.flush();	 Catch:{ all -> 0x002d }
        r2 = r5.underlyingFileOutputStream;	 Catch:{ all -> 0x002d }
        r2 = r2.getFD();	 Catch:{ all -> 0x002d }
        r2.sync();	 Catch:{ all -> 0x002d }
        r1 = 1;
        r2 = r5.outputStream;
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        r5.outputStream = r4;
        r0 = r5.file;
        r5.file = r4;
        if (r1 == 0) goto L_0x0029;
    L_0x0023:
        r2 = r5.cache;
        r2.commitFile(r0);
        goto L_0x0005;
    L_0x0029:
        r0.delete();
        goto L_0x0005;
    L_0x002d:
        r2 = move-exception;
        r3 = r5.outputStream;
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r3);
        r5.outputStream = r4;
        r0 = r5.file;
        r5.file = r4;
        if (r1 == 0) goto L_0x0041;
    L_0x003b:
        r3 = r5.cache;
        r3.commitFile(r0);
    L_0x0040:
        throw r2;
    L_0x0041:
        r0.delete();
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink.closeCurrentOutputStream():void");
    }
}
