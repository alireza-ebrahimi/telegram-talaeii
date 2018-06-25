package android.support.p009b;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;
import org.telegram.messenger.MessagesController;

/* renamed from: android.support.b.c */
final class C0028c {

    /* renamed from: android.support.b.c$a */
    static class C0027a {
        /* renamed from: a */
        long f97a;
        /* renamed from: b */
        long f98b;

        C0027a() {
        }
    }

    /* renamed from: a */
    static long m101a(File file) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            long a = C0028c.m102a(randomAccessFile, C0028c.m103a(randomAccessFile));
            return a;
        } finally {
            randomAccessFile.close();
        }
    }

    /* renamed from: a */
    static long m102a(RandomAccessFile randomAccessFile, C0027a c0027a) {
        CRC32 crc32 = new CRC32();
        long j = c0027a.f98b;
        randomAccessFile.seek(c0027a.f97a);
        byte[] bArr = new byte[MessagesController.UPDATE_MASK_CHAT_ADMINS];
        int read = randomAccessFile.read(bArr, 0, (int) Math.min(16384, j));
        while (read != -1) {
            crc32.update(bArr, 0, read);
            j -= (long) read;
            if (j == 0) {
                break;
            }
            read = randomAccessFile.read(bArr, 0, (int) Math.min(16384, j));
        }
        return crc32.getValue();
    }

    /* renamed from: a */
    static C0027a m103a(RandomAccessFile randomAccessFile) {
        long j = 0;
        long length = randomAccessFile.length() - 22;
        if (length < 0) {
            throw new ZipException("File too short to be a zip file: " + randomAccessFile.length());
        }
        long j2 = length - 65536;
        if (j2 >= 0) {
            j = j2;
        }
        int reverseBytes = Integer.reverseBytes(101010256);
        j2 = length;
        do {
            randomAccessFile.seek(j2);
            if (randomAccessFile.readInt() == reverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                C0027a c0027a = new C0027a();
                c0027a.f98b = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                c0027a.f97a = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                return c0027a;
            }
            j2--;
        } while (j2 >= j);
        throw new ZipException("End Of Central Directory signature not found");
    }
}
