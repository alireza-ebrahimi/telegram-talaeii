package org.telegram.messenger.secretmedia;

import android.net.Uri;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.TransferListener;

public final class EncryptedFileDataSource implements DataSource {
    private long bytesRemaining;
    private RandomAccessFile file;
    private int fileOffset;
    private byte[] iv;
    private byte[] key;
    private final TransferListener<? super EncryptedFileDataSource> listener;
    private boolean opened;
    private Uri uri;

    public static class EncryptedFileDataSourceException extends IOException {
        public EncryptedFileDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public EncryptedFileDataSource() {
        this(null);
    }

    public EncryptedFileDataSource(TransferListener<? super EncryptedFileDataSource> transferListener) {
        this.key = new byte[32];
        this.iv = new byte[16];
        this.listener = transferListener;
    }

    public void close() {
        this.uri = null;
        this.fileOffset = 0;
        try {
            if (this.file != null) {
                this.file.close();
            }
            this.file = null;
            if (this.opened) {
                this.opened = false;
                if (this.listener != null) {
                    this.listener.onTransferEnd(this);
                }
            }
        } catch (IOException e) {
            throw new EncryptedFileDataSourceException(e);
        } catch (Throwable th) {
            this.file = null;
            if (this.opened) {
                this.opened = false;
                if (this.listener != null) {
                    this.listener.onTransferEnd(this);
                }
            }
        }
    }

    public Uri getUri() {
        return this.uri;
    }

    public long open(DataSpec dataSpec) {
        try {
            this.uri = dataSpec.uri;
            File file = new File(dataSpec.uri.getPath());
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FileLoader.getInternalCacheDir(), file.getName() + ".key"), "r");
            randomAccessFile.read(this.key);
            randomAccessFile.read(this.iv);
            randomAccessFile.close();
            this.file = new RandomAccessFile(file, "r");
            this.file.seek(dataSpec.position);
            this.fileOffset = (int) dataSpec.position;
            this.bytesRemaining = dataSpec.length == -1 ? this.file.length() - dataSpec.position : dataSpec.length;
            if (this.bytesRemaining < 0) {
                throw new EOFException();
            }
            this.opened = true;
            if (this.listener != null) {
                this.listener.onTransferStart(this, dataSpec);
            }
            return this.bytesRemaining;
        } catch (IOException e) {
            throw new EncryptedFileDataSourceException(e);
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
            int read = this.file.read(bArr, i, (int) Math.min(this.bytesRemaining, (long) i2));
            Utilities.aesCtrDecryptionByteArray(bArr, this.key, this.iv, i, read, this.fileOffset);
            this.fileOffset += read;
            if (read <= 0) {
                return read;
            }
            this.bytesRemaining -= (long) read;
            if (this.listener == null) {
                return read;
            }
            this.listener.onBytesTransferred(this, read);
            return read;
        } catch (IOException e) {
            throw new EncryptedFileDataSourceException(e);
        }
    }
}
