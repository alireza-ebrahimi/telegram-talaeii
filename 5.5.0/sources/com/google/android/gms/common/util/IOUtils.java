package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import javax.annotation.Nullable;

public final class IOUtils {

    private static final class zza extends ByteArrayOutputStream {
        private zza() {
        }

        final void zza(byte[] bArr, int i) {
            System.arraycopy(this.buf, 0, bArr, i, this.count);
        }
    }

    private static final class zzb {
        private final File file;

        private zzb(File file) {
            this.file = (File) Preconditions.checkNotNull(file);
        }

        public final byte[] zzdd() {
            Closeable fileInputStream;
            Throwable th;
            try {
                fileInputStream = new FileInputStream(this.file);
                try {
                    byte[] zzb = IOUtils.zza((InputStream) fileInputStream, fileInputStream.getChannel().size());
                    IOUtils.closeQuietly(fileInputStream);
                    return zzb;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                IOUtils.closeQuietly(fileInputStream);
                throw th;
            }
        }
    }

    private IOUtils() {
    }

    public static void close(@Nullable Closeable closeable, String str, String str2) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                Log.d(str, str2, e);
            }
        }
    }

    public static void closeQuietly(@Nullable ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(@Nullable ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(@Nullable Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream) {
        return copyStream(inputStream, outputStream, false);
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean z) {
        return copyStream(inputStream, outputStream, z, 1024);
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean z, int i) {
        byte[] bArr = new byte[i];
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(bArr, 0, i);
                if (read == -1) {
                    break;
                }
                j += (long) read;
                outputStream.write(bArr, 0, read);
            } finally {
                if (z) {
                    closeQuietly((Closeable) inputStream);
                    closeQuietly((Closeable) outputStream);
                }
            }
        }
        return j;
    }

    public static boolean isGzipByteBuffer(byte[] bArr) {
        return bArr.length > 1 && ((bArr[0] & 255) | ((bArr[1] & 255) << 8)) == 35615;
    }

    public static void lockAndTruncateFile(File file) {
        Closeable randomAccessFile;
        Throwable th;
        FileLock fileLock = null;
        if (file.exists()) {
            try {
                randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    FileChannel channel = randomAccessFile.getChannel();
                    fileLock = channel.lock();
                    channel.truncate(0);
                    if (fileLock != null && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e) {
                        }
                    }
                    closeQuietly(randomAccessFile);
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        fileLock.release();
                    } catch (IOException e2) {
                    }
                    if (randomAccessFile != null) {
                        closeQuietly(randomAccessFile);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
                if (fileLock != null && fileLock.isValid()) {
                    fileLock.release();
                }
                if (randomAccessFile != null) {
                    closeQuietly(randomAccessFile);
                }
                throw th;
            }
        }
        throw new FileNotFoundException();
    }

    public static byte[] readInputStreamFully(InputStream inputStream) {
        return readInputStreamFully(inputStream, true);
    }

    public static byte[] readInputStreamFully(InputStream inputStream, boolean z) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyStream(inputStream, byteArrayOutputStream, z);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toByteArray(File file) {
        return new zzb(file).zzdd();
    }

    public static byte[] toByteArray(InputStream inputStream) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zza(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static long zza(InputStream inputStream, OutputStream outputStream) {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    private static byte[] zza(InputStream inputStream, long j) {
        if (j > 2147483647L) {
            throw new OutOfMemoryError("file is too large to fit in a byte array: " + j + " bytes");
        } else if (j == 0) {
            return toByteArray(inputStream);
        } else {
            int i = (int) j;
            byte[] bArr = new byte[i];
            int i2 = i;
            while (i2 > 0) {
                int i3 = i - i2;
                int read = inputStream.read(bArr, i3, i2);
                if (read == -1) {
                    return Arrays.copyOf(bArr, i3);
                }
                i2 -= read;
            }
            i2 = inputStream.read();
            if (i2 == -1) {
                return bArr;
            }
            OutputStream zza = new zza();
            zza.write(i2);
            zza(inputStream, zza);
            byte[] copyOf = Arrays.copyOf(bArr, bArr.length + zza.size());
            zza.zza(copyOf, bArr.length);
            return copyOf;
        }
    }
}
