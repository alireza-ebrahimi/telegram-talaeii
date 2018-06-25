package org.telegram.messenger.secretmedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.telegram.messenger.Utilities;

public class EncryptedFileInputStream extends FileInputStream {
    private int fileOffset;
    private byte[] iv = new byte[16];
    private byte[] key = new byte[32];

    public EncryptedFileInputStream(File file, File keyFile) throws Exception {
        super(file);
        RandomAccessFile randomAccessFile = new RandomAccessFile(keyFile, "r");
        randomAccessFile.read(this.key, 0, 32);
        randomAccessFile.read(this.iv, 0, 16);
        randomAccessFile.close();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        Utilities.aesCtrDecryptionByteArray(b, this.key, this.iv, off, len, this.fileOffset);
        this.fileOffset += len;
        return result;
    }

    public long skip(long n) throws IOException {
        this.fileOffset = (int) (((long) this.fileOffset) + n);
        return super.skip(n);
    }

    public static void decryptBytesWithKeyFile(byte[] bytes, int offset, int length, File keyFile) throws Exception {
        byte[] key = new byte[32];
        byte[] iv = new byte[16];
        RandomAccessFile randomAccessFile = new RandomAccessFile(keyFile, "r");
        randomAccessFile.read(key, 0, 32);
        randomAccessFile.read(iv, 0, 16);
        randomAccessFile.close();
        Utilities.aesCtrDecryptionByteArray(bytes, key, iv, offset, length, 0);
    }
}
