package org.telegram.messenger.secretmedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import org.telegram.messenger.Utilities;

public class EncryptedFileInputStream extends FileInputStream {
    private int fileOffset;
    private byte[] iv = new byte[16];
    private byte[] key = new byte[32];

    public EncryptedFileInputStream(File file, File file2) {
        super(file);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "r");
        randomAccessFile.read(this.key, 0, 32);
        randomAccessFile.read(this.iv, 0, 16);
        randomAccessFile.close();
    }

    public static void decryptBytesWithKeyFile(byte[] bArr, int i, int i2, File file) {
        byte[] bArr2 = new byte[32];
        byte[] bArr3 = new byte[16];
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.read(bArr2, 0, 32);
        randomAccessFile.read(bArr3, 0, 16);
        randomAccessFile.close();
        Utilities.aesCtrDecryptionByteArray(bArr, bArr2, bArr3, i, i2, 0);
    }

    public int read(byte[] bArr, int i, int i2) {
        int read = super.read(bArr, i, i2);
        Utilities.aesCtrDecryptionByteArray(bArr, this.key, this.iv, i, i2, this.fileOffset);
        this.fileOffset += i2;
        return read;
    }

    public long skip(long j) {
        this.fileOffset = (int) (((long) this.fileOffset) + j);
        return super.skip(j);
    }
}
