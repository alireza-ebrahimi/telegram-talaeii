package org.telegram.messenger.exoplayer2.source.hls;

import android.net.Uri;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSourceInputStream;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class Aes128DataSource implements DataSource {
    private CipherInputStream cipherInputStream;
    private final byte[] encryptionIv;
    private final byte[] encryptionKey;
    private final DataSource upstream;

    public Aes128DataSource(DataSource dataSource, byte[] bArr, byte[] bArr2) {
        this.upstream = dataSource;
        this.encryptionKey = bArr;
        this.encryptionIv = bArr2;
    }

    public void close() {
        this.cipherInputStream = null;
        this.upstream.close();
    }

    public Uri getUri() {
        return this.upstream.getUri();
    }

    public long open(DataSpec dataSpec) {
        Throwable e;
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            try {
                instance.init(2, new SecretKeySpec(this.encryptionKey, "AES"), new IvParameterSpec(this.encryptionIv));
                this.cipherInputStream = new CipherInputStream(new DataSourceInputStream(this.upstream, dataSpec), instance);
                return -1;
            } catch (InvalidKeyException e2) {
                e = e2;
                throw new RuntimeException(e);
            } catch (InvalidAlgorithmParameterException e3) {
                e = e3;
                throw new RuntimeException(e);
            }
        } catch (NoSuchAlgorithmException e4) {
            e = e4;
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e5) {
            e = e5;
            throw new RuntimeException(e);
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        Assertions.checkState(this.cipherInputStream != null);
        int read = this.cipherInputStream.read(bArr, i, i2);
        return read < 0 ? -1 : read;
    }
}
