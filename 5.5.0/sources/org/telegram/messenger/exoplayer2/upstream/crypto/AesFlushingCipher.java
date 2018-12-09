package org.telegram.messenger.exoplayer2.upstream.crypto;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class AesFlushingCipher {
    private final int blockSize;
    private final Cipher cipher;
    private final byte[] flushedBlock;
    private int pendingXorBytes;
    private final byte[] zerosBlock;

    public AesFlushingCipher(int i, byte[] bArr, long j, long j2) {
        Throwable e;
        try {
            this.cipher = Cipher.getInstance("AES/CTR/NoPadding");
            this.blockSize = this.cipher.getBlockSize();
            this.zerosBlock = new byte[this.blockSize];
            this.flushedBlock = new byte[this.blockSize];
            int i2 = (int) (j2 % ((long) this.blockSize));
            this.cipher.init(i, new SecretKeySpec(bArr, this.cipher.getAlgorithm().split("/")[0]), new IvParameterSpec(getInitializationVector(j, j2 / ((long) this.blockSize))));
            if (i2 != 0) {
                updateInPlace(new byte[i2], 0, i2);
            }
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e3) {
            e = e3;
            throw new RuntimeException(e);
        } catch (InvalidKeyException e4) {
            e = e4;
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e5) {
            e = e5;
            throw new RuntimeException(e);
        }
    }

    private byte[] getInitializationVector(long j, long j2) {
        return ByteBuffer.allocate(16).putLong(j).putLong(j2).array();
    }

    private int nonFlushingUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        try {
            return this.cipher.update(bArr, i, i2, bArr2, i3);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        boolean z = true;
        int i4 = 0;
        int i5 = i3;
        int i6 = i2;
        int i7 = i;
        while (this.pendingXorBytes > 0) {
            bArr2[i5] = (byte) (bArr[i7] ^ this.flushedBlock[this.blockSize - this.pendingXorBytes]);
            i5++;
            i7++;
            this.pendingXorBytes--;
            i6--;
            if (i6 == 0) {
                return;
            }
        }
        int nonFlushingUpdate = nonFlushingUpdate(bArr, i7, i6, bArr2, i5);
        if (i6 != nonFlushingUpdate) {
            int i8 = i6 - nonFlushingUpdate;
            Assertions.checkState(i8 < this.blockSize);
            int i9 = i5 + nonFlushingUpdate;
            this.pendingXorBytes = this.blockSize - i8;
            if (nonFlushingUpdate(this.zerosBlock, 0, this.pendingXorBytes, this.flushedBlock, 0) != this.blockSize) {
                z = false;
            }
            Assertions.checkState(z);
            int i10 = i9;
            while (i4 < i8) {
                nonFlushingUpdate = i10 + 1;
                bArr2[i10] = this.flushedBlock[i4];
                i4++;
                i10 = nonFlushingUpdate;
            }
        }
    }

    public void updateInPlace(byte[] bArr, int i, int i2) {
        update(bArr, i, i2, bArr, i);
    }
}
