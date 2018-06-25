package org.telegram.messenger.exoplayer2.upstream.cache;

import android.util.SparseArray;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.AtomicFile;
import org.telegram.messenger.exoplayer2.util.ReusableBufferedOutputStream;
import org.telegram.messenger.exoplayer2.util.Util;

class CachedContentIndex {
    public static final String FILE_NAME = "cached_content_index.exi";
    private static final int FLAG_ENCRYPTED_INDEX = 1;
    private static final String TAG = "CachedContentIndex";
    private static final int VERSION = 1;
    private final AtomicFile atomicFile;
    private ReusableBufferedOutputStream bufferedOutputStream;
    private boolean changed;
    private final Cipher cipher;
    private final boolean encrypt;
    private final SparseArray<String> idToKey;
    private final HashMap<String, CachedContent> keyToContent;
    private final SecretKeySpec secretKeySpec;

    public CachedContentIndex(File file) {
        this(file, null);
    }

    public CachedContentIndex(File file, byte[] bArr) {
        this(file, bArr, bArr != null);
    }

    public CachedContentIndex(File file, byte[] bArr, boolean z) {
        Throwable e;
        this.encrypt = z;
        if (bArr != null) {
            Assertions.checkArgument(bArr.length == 16);
            try {
                this.cipher = getCipher();
                this.secretKeySpec = new SecretKeySpec(bArr, "AES");
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
                throw new IllegalStateException(e);
            } catch (NoSuchPaddingException e3) {
                e = e3;
                throw new IllegalStateException(e);
            }
        }
        this.cipher = null;
        this.secretKeySpec = null;
        this.keyToContent = new HashMap();
        this.idToKey = new SparseArray();
        this.atomicFile = new AtomicFile(new File(file, FILE_NAME));
    }

    private void add(CachedContent cachedContent) {
        this.keyToContent.put(cachedContent.key, cachedContent);
        this.idToKey.put(cachedContent.id, cachedContent.key);
    }

    private CachedContent addNew(String str, long j) {
        CachedContent cachedContent = new CachedContent(getNewId(this.idToKey), str, j);
        addNew(cachedContent);
        return cachedContent;
    }

    private static Cipher getCipher() {
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
            } catch (Throwable th) {
            }
        }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    public static int getNewId(SparseArray<String> sparseArray) {
        int size = sparseArray.size();
        int keyAt = size == 0 ? 0 : sparseArray.keyAt(size - 1) + 1;
        if (keyAt < 0) {
            keyAt = 0;
            while (keyAt < size && keyAt == sparseArray.keyAt(keyAt)) {
                keyAt++;
            }
        }
        return keyAt;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean readFile() {
        /*
        r8 = this;
        r1 = 1;
        r0 = 0;
        r3 = 0;
        r4 = new java.io.BufferedInputStream;	 Catch:{ FileNotFoundException -> 0x00b2, IOException -> 0x00af, all -> 0x00a5 }
        r2 = r8.atomicFile;	 Catch:{ FileNotFoundException -> 0x00b2, IOException -> 0x00af, all -> 0x00a5 }
        r2 = r2.openRead();	 Catch:{ FileNotFoundException -> 0x00b2, IOException -> 0x00af, all -> 0x00a5 }
        r4.<init>(r2);	 Catch:{ FileNotFoundException -> 0x00b2, IOException -> 0x00af, all -> 0x00a5 }
        r2 = new java.io.DataInputStream;	 Catch:{ FileNotFoundException -> 0x00b2, IOException -> 0x00af, all -> 0x00a5 }
        r2.<init>(r4);	 Catch:{ FileNotFoundException -> 0x00b2, IOException -> 0x00af, all -> 0x00a5 }
        r3 = r2.readInt();	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        if (r3 == r1) goto L_0x001f;
    L_0x0019:
        if (r2 == 0) goto L_0x001e;
    L_0x001b:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
    L_0x001e:
        return r0;
    L_0x001f:
        r3 = r2.readInt();	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r3 = r3 & 1;
        if (r3 == 0) goto L_0x0079;
    L_0x0027:
        r3 = r8.cipher;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        if (r3 != 0) goto L_0x0031;
    L_0x002b:
        if (r2 == 0) goto L_0x001e;
    L_0x002d:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        goto L_0x001e;
    L_0x0031:
        r3 = 16;
        r3 = new byte[r3];	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r2.readFully(r3);	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r5 = new javax.crypto.spec.IvParameterSpec;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r5.<init>(r3);	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r3 = r8.cipher;	 Catch:{ InvalidKeyException -> 0x00b8, InvalidAlgorithmParameterException -> 0x006a }
        r6 = 2;
        r7 = r8.secretKeySpec;	 Catch:{ InvalidKeyException -> 0x00b8, InvalidAlgorithmParameterException -> 0x006a }
        r3.init(r6, r7, r5);	 Catch:{ InvalidKeyException -> 0x00b8, InvalidAlgorithmParameterException -> 0x006a }
        r3 = new java.io.DataInputStream;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r5 = new javax.crypto.CipherInputStream;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r6 = r8.cipher;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r5.<init>(r4, r6);	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r3.<init>(r5);	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r2 = r3;
    L_0x0052:
        r5 = r2.readInt();	 Catch:{ FileNotFoundException -> 0x00b5, IOException -> 0x0081 }
        r3 = r0;
        r4 = r0;
    L_0x0058:
        if (r3 >= r5) goto L_0x0091;
    L_0x005a:
        r6 = new org.telegram.messenger.exoplayer2.upstream.cache.CachedContent;	 Catch:{ FileNotFoundException -> 0x00b5, IOException -> 0x0081 }
        r6.<init>(r2);	 Catch:{ FileNotFoundException -> 0x00b5, IOException -> 0x0081 }
        r8.add(r6);	 Catch:{ FileNotFoundException -> 0x00b5, IOException -> 0x0081 }
        r6 = r6.headerHashCode();	 Catch:{ FileNotFoundException -> 0x00b5, IOException -> 0x0081 }
        r4 = r4 + r6;
        r3 = r3 + 1;
        goto L_0x0058;
    L_0x006a:
        r1 = move-exception;
    L_0x006b:
        r3 = new java.lang.IllegalStateException;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        r3.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        throw r3;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
    L_0x0071:
        r1 = move-exception;
        r1 = r2;
    L_0x0073:
        if (r1 == 0) goto L_0x001e;
    L_0x0075:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r1);
        goto L_0x001e;
    L_0x0079:
        r3 = r8.cipher;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        if (r3 == 0) goto L_0x0052;
    L_0x007d:
        r3 = 1;
        r8.changed = r3;	 Catch:{ FileNotFoundException -> 0x0071, IOException -> 0x0081 }
        goto L_0x0052;
    L_0x0081:
        r1 = move-exception;
    L_0x0082:
        r3 = "CachedContentIndex";
        r4 = "Error reading cache content index file.";
        android.util.Log.e(r3, r4, r1);	 Catch:{ all -> 0x00ad }
        if (r2 == 0) goto L_0x001e;
    L_0x008d:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        goto L_0x001e;
    L_0x0091:
        r3 = r2.readInt();	 Catch:{ FileNotFoundException -> 0x00b5, IOException -> 0x0081 }
        if (r3 == r4) goto L_0x009d;
    L_0x0097:
        if (r2 == 0) goto L_0x001e;
    L_0x0099:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        goto L_0x001e;
    L_0x009d:
        if (r2 == 0) goto L_0x00a2;
    L_0x009f:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
    L_0x00a2:
        r0 = r1;
        goto L_0x001e;
    L_0x00a5:
        r0 = move-exception;
        r2 = r3;
    L_0x00a7:
        if (r2 == 0) goto L_0x00ac;
    L_0x00a9:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
    L_0x00ac:
        throw r0;
    L_0x00ad:
        r0 = move-exception;
        goto L_0x00a7;
    L_0x00af:
        r1 = move-exception;
        r2 = r3;
        goto L_0x0082;
    L_0x00b2:
        r1 = move-exception;
        r1 = r3;
        goto L_0x0073;
    L_0x00b5:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0073;
    L_0x00b8:
        r1 = move-exception;
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.cache.CachedContentIndex.readFile():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeFile() {
        /*
        r7 = this;
        r1 = 0;
        r0 = 0;
        r3 = 1;
        r2 = r7.atomicFile;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r2 = r2.startWrite();	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r4 = r7.bufferedOutputStream;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        if (r4 != 0) goto L_0x0087;
    L_0x000d:
        r4 = new org.telegram.messenger.exoplayer2.util.ReusableBufferedOutputStream;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r4.<init>(r2);	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r7.bufferedOutputStream = r4;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
    L_0x0014:
        r2 = new java.io.DataOutputStream;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r4 = r7.bufferedOutputStream;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r2.<init>(r4);	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r4 = 1;
        r2.writeInt(r4);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4 = r7.encrypt;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        if (r4 == 0) goto L_0x009a;
    L_0x0023:
        r4 = r7.cipher;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        if (r4 == 0) goto L_0x009a;
    L_0x0027:
        r4 = r3;
    L_0x0028:
        if (r4 == 0) goto L_0x009c;
    L_0x002a:
        r2.writeInt(r3);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        if (r4 == 0) goto L_0x005d;
    L_0x002f:
        r3 = 16;
        r3 = new byte[r3];	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4 = new java.util.Random;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4.<init>();	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4.nextBytes(r3);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r2.write(r3);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4 = new javax.crypto.spec.IvParameterSpec;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4.<init>(r3);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r3 = r7.cipher;	 Catch:{ InvalidKeyException -> 0x00bc, InvalidAlgorithmParameterException -> 0x009e }
        r5 = 1;
        r6 = r7.secretKeySpec;	 Catch:{ InvalidKeyException -> 0x00bc, InvalidAlgorithmParameterException -> 0x009e }
        r3.init(r5, r6, r4);	 Catch:{ InvalidKeyException -> 0x00bc, InvalidAlgorithmParameterException -> 0x009e }
        r2.flush();	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r3 = new java.io.DataOutputStream;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4 = new javax.crypto.CipherOutputStream;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r5 = r7.bufferedOutputStream;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r6 = r7.cipher;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r4.<init>(r5, r6);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r3.<init>(r4);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r2 = r3;
    L_0x005d:
        r3 = r7.keyToContent;	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r3 = r3.size();	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r2.writeInt(r3);	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r3 = r7.keyToContent;	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r3 = r3.values();	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r4 = r3.iterator();	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r3 = r0;
    L_0x0071:
        r0 = r4.hasNext();	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        if (r0 == 0) goto L_0x00a8;
    L_0x0077:
        r0 = r4.next();	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r0 = (org.telegram.messenger.exoplayer2.upstream.cache.CachedContent) r0;	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r0.writeToStream(r2);	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r0 = r0.headerHashCode();	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r0 = r0 + r3;
        r3 = r0;
        goto L_0x0071;
    L_0x0087:
        r4 = r7.bufferedOutputStream;	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        r4.reset(r2);	 Catch:{ IOException -> 0x008d, all -> 0x00b4 }
        goto L_0x0014;
    L_0x008d:
        r0 = move-exception;
    L_0x008e:
        r2 = new org.telegram.messenger.exoplayer2.upstream.cache.Cache$CacheException;	 Catch:{ all -> 0x0094 }
        r2.<init>(r0);	 Catch:{ all -> 0x0094 }
        throw r2;	 Catch:{ all -> 0x0094 }
    L_0x0094:
        r0 = move-exception;
        r2 = r1;
    L_0x0096:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        throw r0;
    L_0x009a:
        r4 = r0;
        goto L_0x0028;
    L_0x009c:
        r3 = r0;
        goto L_0x002a;
    L_0x009e:
        r0 = move-exception;
    L_0x009f:
        r1 = new java.lang.IllegalStateException;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        r1.<init>(r0);	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
        throw r1;	 Catch:{ IOException -> 0x00a5, all -> 0x00b7 }
    L_0x00a5:
        r0 = move-exception;
        r1 = r2;
        goto L_0x008e;
    L_0x00a8:
        r2.writeInt(r3);	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r0 = r7.atomicFile;	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        r0.endWrite(r2);	 Catch:{ IOException -> 0x00b9, all -> 0x00b7 }
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r1);
        return;
    L_0x00b4:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0096;
    L_0x00b7:
        r0 = move-exception;
        goto L_0x0096;
    L_0x00b9:
        r0 = move-exception;
        r1 = r2;
        goto L_0x008e;
    L_0x00bc:
        r0 = move-exception;
        goto L_0x009f;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.cache.CachedContentIndex.writeFile():void");
    }

    public CachedContent add(String str) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.get(str);
        return cachedContent == null ? addNew(str, -1) : cachedContent;
    }

    void addNew(CachedContent cachedContent) {
        add(cachedContent);
        this.changed = true;
    }

    public int assignIdForKey(String str) {
        return add(str).id;
    }

    public CachedContent get(String str) {
        return (CachedContent) this.keyToContent.get(str);
    }

    public Collection<CachedContent> getAll() {
        return this.keyToContent.values();
    }

    public long getContentLength(String str) {
        CachedContent cachedContent = get(str);
        return cachedContent == null ? -1 : cachedContent.getLength();
    }

    public String getKeyForId(int i) {
        return (String) this.idToKey.get(i);
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void load() {
        Assertions.checkState(!this.changed);
        if (!readFile()) {
            this.atomicFile.delete();
            this.keyToContent.clear();
            this.idToKey.clear();
        }
    }

    public void removeEmpty() {
        LinkedList linkedList = new LinkedList();
        for (CachedContent cachedContent : this.keyToContent.values()) {
            if (cachedContent.isEmpty()) {
                linkedList.add(cachedContent.key);
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            removeEmpty((String) it.next());
        }
    }

    public void removeEmpty(String str) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.remove(str);
        if (cachedContent != null) {
            Assertions.checkState(cachedContent.isEmpty());
            this.idToKey.remove(cachedContent.id);
            this.changed = true;
        }
    }

    public void setContentLength(String str, long j) {
        CachedContent cachedContent = get(str);
        if (cachedContent == null) {
            addNew(str, j);
        } else if (cachedContent.getLength() != j) {
            cachedContent.setLength(j);
            this.changed = true;
        }
    }

    public void store() {
        if (this.changed) {
            writeFile();
            this.changed = false;
        }
    }
}
