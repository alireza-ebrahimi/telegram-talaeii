package utils.volley.toolbox;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import utils.volley.Cache;
import utils.volley.Cache.Entry;
import utils.volley.Header;
import utils.volley.VolleyLog;

public class DiskBasedCache implements Cache {
    private static final int CACHE_MAGIC = 538247942;
    private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
    private static final float HYSTERESIS_FACTOR = 0.9f;
    private final Map<String, CacheHeader> mEntries;
    private final int mMaxCacheSizeInBytes;
    private final File mRootDirectory;
    private long mTotalSize;

    static class CacheHeader {
        final List<Header> allResponseHeaders;
        final String etag;
        final String key;
        final long lastModified;
        final long serverDate;
        long size;
        final long softTtl;
        final long ttl;

        private CacheHeader(String str, String str2, long j, long j2, long j3, long j4, List<Header> list) {
            this.key = str;
            if (TtmlNode.ANONYMOUS_REGION_ID.equals(str2)) {
                str2 = null;
            }
            this.etag = str2;
            this.serverDate = j;
            this.lastModified = j2;
            this.ttl = j3;
            this.softTtl = j4;
            this.allResponseHeaders = list;
        }

        CacheHeader(String str, Entry entry) {
            this(str, entry.etag, entry.serverDate, entry.lastModified, entry.ttl, entry.softTtl, getAllResponseHeaders(entry));
            this.size = (long) entry.data.length;
        }

        private static List<Header> getAllResponseHeaders(Entry entry) {
            return entry.allResponseHeaders != null ? entry.allResponseHeaders : HttpHeaderParser.toAllHeaderList(entry.responseHeaders);
        }

        static CacheHeader readHeader(CountingInputStream countingInputStream) {
            if (DiskBasedCache.readInt(countingInputStream) == DiskBasedCache.CACHE_MAGIC) {
                return new CacheHeader(DiskBasedCache.readString(countingInputStream), DiskBasedCache.readString(countingInputStream), DiskBasedCache.readLong(countingInputStream), DiskBasedCache.readLong(countingInputStream), DiskBasedCache.readLong(countingInputStream), DiskBasedCache.readLong(countingInputStream), DiskBasedCache.readHeaderList(countingInputStream));
            }
            throw new IOException();
        }

        Entry toCacheEntry(byte[] bArr) {
            Entry entry = new Entry();
            entry.data = bArr;
            entry.etag = this.etag;
            entry.serverDate = this.serverDate;
            entry.lastModified = this.lastModified;
            entry.ttl = this.ttl;
            entry.softTtl = this.softTtl;
            entry.responseHeaders = HttpHeaderParser.toHeaderMap(this.allResponseHeaders);
            entry.allResponseHeaders = Collections.unmodifiableList(this.allResponseHeaders);
            return entry;
        }

        boolean writeHeader(OutputStream outputStream) {
            try {
                DiskBasedCache.writeInt(outputStream, DiskBasedCache.CACHE_MAGIC);
                DiskBasedCache.writeString(outputStream, this.key);
                DiskBasedCache.writeString(outputStream, this.etag == null ? TtmlNode.ANONYMOUS_REGION_ID : this.etag);
                DiskBasedCache.writeLong(outputStream, this.serverDate);
                DiskBasedCache.writeLong(outputStream, this.lastModified);
                DiskBasedCache.writeLong(outputStream, this.ttl);
                DiskBasedCache.writeLong(outputStream, this.softTtl);
                DiskBasedCache.writeHeaderList(this.allResponseHeaders, outputStream);
                outputStream.flush();
                return true;
            } catch (IOException e) {
                VolleyLog.m14406d("%s", e.toString());
                return false;
            }
        }
    }

    static class CountingInputStream extends FilterInputStream {
        private long bytesRead;
        private final long length;

        CountingInputStream(InputStream inputStream, long j) {
            super(inputStream);
            this.length = j;
        }

        long bytesRead() {
            return this.bytesRead;
        }

        long bytesRemaining() {
            return this.length - this.bytesRead;
        }

        public int read() {
            int read = super.read();
            if (read != -1) {
                this.bytesRead++;
            }
            return read;
        }

        public int read(byte[] bArr, int i, int i2) {
            int read = super.read(bArr, i, i2);
            if (read != -1) {
                this.bytesRead += (long) read;
            }
            return read;
        }
    }

    public DiskBasedCache(File file) {
        this(file, DEFAULT_DISK_USAGE_BYTES);
    }

    public DiskBasedCache(File file, int i) {
        this.mEntries = new LinkedHashMap(16, 0.75f, true);
        this.mTotalSize = 0;
        this.mRootDirectory = file;
        this.mMaxCacheSizeInBytes = i;
    }

    private String getFilenameForKey(String str) {
        int length = str.length() / 2;
        return String.valueOf(str.substring(0, length).hashCode()) + String.valueOf(str.substring(length).hashCode());
    }

    private void pruneIfNeeded(int i) {
        if (this.mTotalSize + ((long) i) >= ((long) this.mMaxCacheSizeInBytes)) {
            int i2;
            if (VolleyLog.DEBUG) {
                VolleyLog.m14409v("Pruning old cache entries.", new Object[0]);
            }
            long j = this.mTotalSize;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Iterator it = this.mEntries.entrySet().iterator();
            int i3 = 0;
            while (it.hasNext()) {
                CacheHeader cacheHeader = (CacheHeader) ((Map.Entry) it.next()).getValue();
                if (getFileForKey(cacheHeader.key).delete()) {
                    this.mTotalSize -= cacheHeader.size;
                } else {
                    VolleyLog.m14406d("Could not delete cache entry for key=%s, filename=%s", cacheHeader.key, getFilenameForKey(cacheHeader.key));
                }
                it.remove();
                i2 = i3 + 1;
                if (((float) (this.mTotalSize + ((long) i))) < ((float) this.mMaxCacheSizeInBytes) * HYSTERESIS_FACTOR) {
                    break;
                }
                i3 = i2;
            }
            i2 = i3;
            if (VolleyLog.DEBUG) {
                VolleyLog.m14409v("pruned %d files, %d bytes, %d ms", Integer.valueOf(i2), Long.valueOf(this.mTotalSize - j), Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
            }
        }
    }

    private void putEntry(String str, CacheHeader cacheHeader) {
        if (this.mEntries.containsKey(str)) {
            CacheHeader cacheHeader2 = (CacheHeader) this.mEntries.get(str);
            this.mTotalSize = (cacheHeader.size - cacheHeader2.size) + this.mTotalSize;
        } else {
            this.mTotalSize += cacheHeader.size;
        }
        this.mEntries.put(str, cacheHeader);
    }

    private static int read(InputStream inputStream) {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    static List<Header> readHeaderList(CountingInputStream countingInputStream) {
        int readInt = readInt(countingInputStream);
        if (readInt < 0) {
            throw new IOException("readHeaderList size=" + readInt);
        }
        List<Header> emptyList = readInt == 0 ? Collections.emptyList() : new ArrayList();
        for (int i = 0; i < readInt; i++) {
            emptyList.add(new Header(readString(countingInputStream).intern(), readString(countingInputStream).intern()));
        }
        return emptyList;
    }

    static int readInt(InputStream inputStream) {
        return (((0 | (read(inputStream) << 0)) | (read(inputStream) << 8)) | (read(inputStream) << 16)) | (read(inputStream) << 24);
    }

    static long readLong(InputStream inputStream) {
        return (((((((0 | ((((long) read(inputStream)) & 255) << null)) | ((((long) read(inputStream)) & 255) << 8)) | ((((long) read(inputStream)) & 255) << 16)) | ((((long) read(inputStream)) & 255) << 24)) | ((((long) read(inputStream)) & 255) << 32)) | ((((long) read(inputStream)) & 255) << 40)) | ((((long) read(inputStream)) & 255) << 48)) | ((((long) read(inputStream)) & 255) << 56);
    }

    static String readString(CountingInputStream countingInputStream) {
        return new String(streamToBytes(countingInputStream, readLong(countingInputStream)), C3446C.UTF8_NAME);
    }

    private void removeEntry(String str) {
        CacheHeader cacheHeader = (CacheHeader) this.mEntries.remove(str);
        if (cacheHeader != null) {
            this.mTotalSize -= cacheHeader.size;
        }
    }

    static byte[] streamToBytes(CountingInputStream countingInputStream, long j) {
        long bytesRemaining = countingInputStream.bytesRemaining();
        if (j < 0 || j > bytesRemaining || ((long) ((int) j)) != j) {
            throw new IOException("streamToBytes length=" + j + ", maxLength=" + bytesRemaining);
        }
        byte[] bArr = new byte[((int) j)];
        new DataInputStream(countingInputStream).readFully(bArr);
        return bArr;
    }

    static void writeHeaderList(List<Header> list, OutputStream outputStream) {
        if (list != null) {
            writeInt(outputStream, list.size());
            for (Header header : list) {
                writeString(outputStream, header.getName());
                writeString(outputStream, header.getValue());
            }
            return;
        }
        writeInt(outputStream, 0);
    }

    static void writeInt(OutputStream outputStream, int i) {
        outputStream.write((i >> 0) & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write((i >> 24) & 255);
    }

    static void writeLong(OutputStream outputStream, long j) {
        outputStream.write((byte) ((int) (j >>> null)));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void writeString(OutputStream outputStream, String str) {
        byte[] bytes = str.getBytes(C3446C.UTF8_NAME);
        writeLong(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    public synchronized void clear() {
        synchronized (this) {
            File[] listFiles = this.mRootDirectory.listFiles();
            if (listFiles != null) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
            this.mEntries.clear();
            this.mTotalSize = 0;
            VolleyLog.m14406d("Cache cleared.", new Object[0]);
        }
    }

    InputStream createInputStream(File file) {
        return new FileInputStream(file);
    }

    OutputStream createOutputStream(File file) {
        return new FileOutputStream(file);
    }

    public synchronized Entry get(String str) {
        Entry entry;
        CacheHeader cacheHeader = (CacheHeader) this.mEntries.get(str);
        if (cacheHeader == null) {
            entry = null;
        } else {
            File fileForKey = getFileForKey(str);
            CountingInputStream countingInputStream;
            try {
                countingInputStream = new CountingInputStream(new BufferedInputStream(createInputStream(fileForKey)), fileForKey.length());
                if (TextUtils.equals(str, CacheHeader.readHeader(countingInputStream).key)) {
                    entry = cacheHeader.toCacheEntry(streamToBytes(countingInputStream, countingInputStream.bytesRemaining()));
                    countingInputStream.close();
                } else {
                    VolleyLog.m14406d("%s: key=%s, found=%s", fileForKey.getAbsolutePath(), str, CacheHeader.readHeader(countingInputStream).key);
                    removeEntry(str);
                    countingInputStream.close();
                    entry = null;
                }
            } catch (IOException e) {
                VolleyLog.m14406d("%s: %s", fileForKey.getAbsolutePath(), e.toString());
                remove(str);
                entry = null;
            } catch (Throwable th) {
                countingInputStream.close();
            }
        }
        return entry;
    }

    public File getFileForKey(String str) {
        return new File(this.mRootDirectory, getFilenameForKey(str));
    }

    public synchronized void initialize() {
        if (this.mRootDirectory.exists()) {
            File[] listFiles = this.mRootDirectory.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    CountingInputStream countingInputStream;
                    try {
                        long length = file.length();
                        countingInputStream = new CountingInputStream(new BufferedInputStream(createInputStream(file)), length);
                        CacheHeader readHeader = CacheHeader.readHeader(countingInputStream);
                        readHeader.size = length;
                        putEntry(readHeader.key, readHeader);
                        countingInputStream.close();
                    } catch (IOException e) {
                        file.delete();
                    } catch (Throwable th) {
                        countingInputStream.close();
                    }
                }
            }
        } else if (!this.mRootDirectory.mkdirs()) {
            VolleyLog.m14407e("Unable to create cache dir %s", this.mRootDirectory.getAbsolutePath());
        }
    }

    public synchronized void invalidate(String str, boolean z) {
        Entry entry = get(str);
        if (entry != null) {
            entry.softTtl = 0;
            if (z) {
                entry.ttl = 0;
            }
            put(str, entry);
        }
    }

    public synchronized void put(String str, Entry entry) {
        pruneIfNeeded(entry.data.length);
        File fileForKey = getFileForKey(str);
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(createOutputStream(fileForKey));
            CacheHeader cacheHeader = new CacheHeader(str, entry);
            if (cacheHeader.writeHeader(bufferedOutputStream)) {
                bufferedOutputStream.write(entry.data);
                bufferedOutputStream.close();
                putEntry(str, cacheHeader);
            } else {
                bufferedOutputStream.close();
                VolleyLog.m14406d("Failed to write header for %s", fileForKey.getAbsolutePath());
                throw new IOException();
            }
        } catch (IOException e) {
            if (!fileForKey.delete()) {
                VolleyLog.m14406d("Could not clean up file %s", fileForKey.getAbsolutePath());
            }
        }
    }

    public synchronized void remove(String str) {
        boolean delete = getFileForKey(str).delete();
        removeEntry(str);
        if (!delete) {
            VolleyLog.m14406d("Could not delete cache entry for key=%s, filename=%s", str, getFilenameForKey(str));
        }
    }
}
