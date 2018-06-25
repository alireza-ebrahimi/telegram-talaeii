package org.telegram.messenger.exoplayer2.upstream.cache;

import android.util.Log;
import android.util.SparseArray;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.upstream.cache.Cache.CacheException;
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

    public CachedContentIndex(File cacheDir) {
        this(cacheDir, null);
    }

    public CachedContentIndex(File cacheDir, byte[] secretKey) {
        this(cacheDir, secretKey, secretKey != null);
    }

    public CachedContentIndex(File cacheDir, byte[] secretKey, boolean encrypt) {
        GeneralSecurityException e;
        this.encrypt = encrypt;
        if (secretKey != null) {
            Assertions.checkArgument(secretKey.length == 16);
            try {
                this.cipher = getCipher();
                this.secretKeySpec = new SecretKeySpec(secretKey, "AES");
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
        this.atomicFile = new AtomicFile(new File(cacheDir, FILE_NAME));
    }

    public void load() {
        Assertions.checkState(!this.changed);
        if (!readFile()) {
            this.atomicFile.delete();
            this.keyToContent.clear();
            this.idToKey.clear();
        }
    }

    public void store() throws CacheException {
        if (this.changed) {
            writeFile();
            this.changed = false;
        }
    }

    public CachedContent add(String key) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.get(key);
        if (cachedContent == null) {
            return addNew(key, -1);
        }
        return cachedContent;
    }

    public CachedContent get(String key) {
        return (CachedContent) this.keyToContent.get(key);
    }

    public Collection<CachedContent> getAll() {
        return this.keyToContent.values();
    }

    public int assignIdForKey(String key) {
        return add(key).id;
    }

    public String getKeyForId(int id) {
        return (String) this.idToKey.get(id);
    }

    public void removeEmpty(String key) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.remove(key);
        if (cachedContent != null) {
            Assertions.checkState(cachedContent.isEmpty());
            this.idToKey.remove(cachedContent.id);
            this.changed = true;
        }
    }

    public void removeEmpty() {
        LinkedList<String> cachedContentToBeRemoved = new LinkedList();
        for (CachedContent cachedContent : this.keyToContent.values()) {
            if (cachedContent.isEmpty()) {
                cachedContentToBeRemoved.add(cachedContent.key);
            }
        }
        Iterator it = cachedContentToBeRemoved.iterator();
        while (it.hasNext()) {
            removeEmpty((String) it.next());
        }
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void setContentLength(String key, long length) {
        CachedContent cachedContent = get(key);
        if (cachedContent == null) {
            addNew(key, length);
        } else if (cachedContent.getLength() != length) {
            cachedContent.setLength(length);
            this.changed = true;
        }
    }

    public long getContentLength(String key) {
        CachedContent cachedContent = get(key);
        return cachedContent == null ? -1 : cachedContent.getLength();
    }

    private boolean readFile() {
        GeneralSecurityException e;
        IOException e2;
        Throwable th;
        DataInputStream dataInputStream = null;
        try {
            InputStream inputStream = new BufferedInputStream(this.atomicFile.openRead());
            DataInputStream input = new DataInputStream(inputStream);
            try {
                if (input.readInt() != 1) {
                    if (input != null) {
                        Util.closeQuietly(input);
                    }
                    dataInputStream = input;
                    return false;
                }
                if ((input.readInt() & 1) == 0) {
                    if (this.cipher != null) {
                        this.changed = true;
                    }
                    dataInputStream = input;
                } else if (this.cipher == null) {
                    if (input != null) {
                        Util.closeQuietly(input);
                    }
                    dataInputStream = input;
                    return false;
                } else {
                    byte[] initializationVector = new byte[16];
                    input.readFully(initializationVector);
                    try {
                        this.cipher.init(2, this.secretKeySpec, new IvParameterSpec(initializationVector));
                        dataInputStream = new DataInputStream(new CipherInputStream(inputStream, this.cipher));
                    } catch (GeneralSecurityException e3) {
                        e = e3;
                        throw new IllegalStateException(e);
                    } catch (GeneralSecurityException e32) {
                        e = e32;
                        throw new IllegalStateException(e);
                    }
                }
                int count = dataInputStream.readInt();
                int hashCode = 0;
                for (int i = 0; i < count; i++) {
                    CachedContent cachedContent = new CachedContent(dataInputStream);
                    add(cachedContent);
                    hashCode += cachedContent.headerHashCode();
                }
                if (dataInputStream.readInt() == hashCode) {
                    if (dataInputStream != null) {
                        Util.closeQuietly(dataInputStream);
                    }
                    return true;
                } else if (dataInputStream == null) {
                    return false;
                } else {
                    Util.closeQuietly(dataInputStream);
                    return false;
                }
            } catch (FileNotFoundException e4) {
                dataInputStream = input;
                if (dataInputStream != null) {
                    return false;
                }
                Util.closeQuietly(dataInputStream);
                return false;
            } catch (IOException e5) {
                e2 = e5;
                dataInputStream = input;
                try {
                    Log.e(TAG, "Error reading cache content index file.", e2);
                    if (dataInputStream != null) {
                        return false;
                    }
                    Util.closeQuietly(dataInputStream);
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    if (dataInputStream != null) {
                        Util.closeQuietly(dataInputStream);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataInputStream = input;
                if (dataInputStream != null) {
                    Util.closeQuietly(dataInputStream);
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            if (dataInputStream != null) {
                return false;
            }
            Util.closeQuietly(dataInputStream);
            return false;
        } catch (IOException e7) {
            e2 = e7;
            Log.e(TAG, "Error reading cache content index file.", e2);
            if (dataInputStream != null) {
                return false;
            }
            Util.closeQuietly(dataInputStream);
            return false;
        }
    }

    private void writeFile() throws CacheException {
        GeneralSecurityException e;
        IOException e2;
        Throwable th;
        int flags = 1;
        DataOutputStream dataOutputStream = null;
        try {
            OutputStream outputStream = this.atomicFile.startWrite();
            if (this.bufferedOutputStream == null) {
                this.bufferedOutputStream = new ReusableBufferedOutputStream(outputStream);
            } else {
                this.bufferedOutputStream.reset(outputStream);
            }
            DataOutputStream output = new DataOutputStream(this.bufferedOutputStream);
            try {
                boolean writeEncrypted;
                output.writeInt(1);
                if (!this.encrypt || this.cipher == null) {
                    writeEncrypted = false;
                } else {
                    writeEncrypted = true;
                }
                if (!writeEncrypted) {
                    flags = 0;
                }
                output.writeInt(flags);
                if (writeEncrypted) {
                    byte[] initializationVector = new byte[16];
                    new Random().nextBytes(initializationVector);
                    output.write(initializationVector);
                    try {
                        this.cipher.init(1, this.secretKeySpec, new IvParameterSpec(initializationVector));
                        output.flush();
                        dataOutputStream = new DataOutputStream(new CipherOutputStream(this.bufferedOutputStream, this.cipher));
                    } catch (GeneralSecurityException e3) {
                        e = e3;
                        throw new IllegalStateException(e);
                    } catch (GeneralSecurityException e32) {
                        e = e32;
                        throw new IllegalStateException(e);
                    }
                }
                dataOutputStream = output;
                dataOutputStream.writeInt(this.keyToContent.size());
                int hashCode = 0;
                for (CachedContent cachedContent : this.keyToContent.values()) {
                    cachedContent.writeToStream(dataOutputStream);
                    hashCode += cachedContent.headerHashCode();
                }
                dataOutputStream.writeInt(hashCode);
                this.atomicFile.endWrite(dataOutputStream);
                Util.closeQuietly(null);
            } catch (IOException e4) {
                e2 = e4;
                dataOutputStream = output;
                try {
                    throw new CacheException(e2);
                } catch (Throwable th2) {
                    th = th2;
                    Util.closeQuietly(dataOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = output;
                Util.closeQuietly(dataOutputStream);
                throw th;
            }
        } catch (IOException e5) {
            e2 = e5;
            throw new CacheException(e2);
        }
    }

    private void add(CachedContent cachedContent) {
        this.keyToContent.put(cachedContent.key, cachedContent);
        this.idToKey.put(cachedContent.id, cachedContent.key);
    }

    void addNew(CachedContent cachedContent) {
        add(cachedContent);
        this.changed = true;
    }

    private CachedContent addNew(String key, long length) {
        CachedContent cachedContent = new CachedContent(getNewId(this.idToKey), key, length);
        addNew(cachedContent);
        return cachedContent;
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
            } catch (Throwable th) {
            }
        }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    public static int getNewId(SparseArray<String> idToKey) {
        int size = idToKey.size();
        int id = size == 0 ? 0 : idToKey.keyAt(size - 1) + 1;
        if (id < 0) {
            id = 0;
            while (id < size && id == idToKey.keyAt(id)) {
                id++;
            }
        }
        return id;
    }
}
