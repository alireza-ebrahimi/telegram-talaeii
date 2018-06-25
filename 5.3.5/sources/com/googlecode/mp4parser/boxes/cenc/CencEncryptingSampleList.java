package com.googlecode.mp4parser.boxes.cenc;

import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.RangeStartMap;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import org.telegram.messenger.exoplayer2.C0907C;

public class CencEncryptingSampleList extends AbstractList<Sample> {
    List<CencSampleAuxiliaryDataFormat> auxiliaryDataFormats;
    RangeStartMap<Integer, SecretKey> ceks;
    Cipher cipher;
    private final String encryptionAlgo;
    List<Sample> parent;

    private class EncryptedSampleImpl implements Sample {
        static final /* synthetic */ boolean $assertionsDisabled = (!CencEncryptingSampleList.class.desiredAssertionStatus());
        private final SecretKey cek;
        private final CencSampleAuxiliaryDataFormat cencSampleAuxiliaryDataFormat;
        private final Cipher cipher;
        private final Sample clearSample;

        private EncryptedSampleImpl(Sample clearSample, CencSampleAuxiliaryDataFormat cencSampleAuxiliaryDataFormat, Cipher cipher, SecretKey cek) {
            this.clearSample = clearSample;
            this.cencSampleAuxiliaryDataFormat = cencSampleAuxiliaryDataFormat;
            this.cipher = cipher;
            this.cek = cek;
        }

        public void writeTo(WritableByteChannel channel) throws IOException {
            ByteBuffer sample = (ByteBuffer) this.clearSample.asByteBuffer().rewind();
            CencEncryptingSampleList.this.initCipher(this.cencSampleAuxiliaryDataFormat.iv, this.cek);
            try {
                if (this.cencSampleAuxiliaryDataFormat.pairs == null || this.cencSampleAuxiliaryDataFormat.pairs.length <= 0) {
                    byte[] fullyEncryptedSample = new byte[sample.limit()];
                    sample.get(fullyEncryptedSample);
                    if (C0907C.CENC_TYPE_cbc1.equals(CencEncryptingSampleList.this.encryptionAlgo)) {
                        int encryptedLength = (fullyEncryptedSample.length / 16) * 16;
                        channel.write(ByteBuffer.wrap(this.cipher.doFinal(fullyEncryptedSample, 0, encryptedLength)));
                        channel.write(ByteBuffer.wrap(fullyEncryptedSample, encryptedLength, fullyEncryptedSample.length - encryptedLength));
                    } else if (C0907C.CENC_TYPE_cenc.equals(CencEncryptingSampleList.this.encryptionAlgo)) {
                        channel.write(ByteBuffer.wrap(this.cipher.doFinal(fullyEncryptedSample)));
                    }
                } else {
                    byte[] fullSample = new byte[sample.limit()];
                    sample.get(fullSample);
                    int offset = 0;
                    for (Pair pair : this.cencSampleAuxiliaryDataFormat.pairs) {
                        offset += pair.clear();
                        if (pair.encrypted() > 0) {
                            this.cipher.update(fullSample, offset, CastUtils.l2i(pair.encrypted()), fullSample, offset);
                            offset = (int) (((long) offset) + pair.encrypted());
                        }
                    }
                    channel.write(ByteBuffer.wrap(fullSample));
                }
                sample.rewind();
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e2) {
                throw new RuntimeException(e2);
            } catch (ShortBufferException e3) {
                throw new RuntimeException(e3);
            }
        }

        public long getSize() {
            return this.clearSample.getSize();
        }

        public ByteBuffer asByteBuffer() {
            ByteBuffer sample = (ByteBuffer) this.clearSample.asByteBuffer().rewind();
            ByteBuffer encSample = ByteBuffer.allocate(sample.limit());
            CencSampleAuxiliaryDataFormat entry = this.cencSampleAuxiliaryDataFormat;
            CencEncryptingSampleList.this.initCipher(this.cencSampleAuxiliaryDataFormat.iv, this.cek);
            try {
                if (entry.pairs != null) {
                    for (Pair pair : entry.pairs) {
                        byte[] clears = new byte[pair.clear()];
                        sample.get(clears);
                        encSample.put(clears);
                        if (pair.encrypted() > 0) {
                            byte[] toBeEncrypted = new byte[CastUtils.l2i(pair.encrypted())];
                            sample.get(toBeEncrypted);
                            if ($assertionsDisabled || toBeEncrypted.length % 16 == 0) {
                                byte[] encrypted = this.cipher.update(toBeEncrypted);
                                if ($assertionsDisabled || encrypted.length == toBeEncrypted.length) {
                                    encSample.put(encrypted);
                                } else {
                                    throw new AssertionError();
                                }
                            }
                            throw new AssertionError();
                        }
                    }
                } else {
                    byte[] fullyEncryptedSample = new byte[sample.limit()];
                    sample.get(fullyEncryptedSample);
                    if (C0907C.CENC_TYPE_cbc1.equals(CencEncryptingSampleList.this.encryptionAlgo)) {
                        int encryptedLength = (fullyEncryptedSample.length / 16) * 16;
                        encSample.put(this.cipher.doFinal(fullyEncryptedSample, 0, encryptedLength));
                        encSample.put(fullyEncryptedSample, encryptedLength, fullyEncryptedSample.length - encryptedLength);
                    } else if (C0907C.CENC_TYPE_cenc.equals(CencEncryptingSampleList.this.encryptionAlgo)) {
                        encSample.put(this.cipher.doFinal(fullyEncryptedSample));
                    }
                }
                sample.rewind();
                encSample.rewind();
                return encSample;
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public CencEncryptingSampleList(SecretKey defaultCek, List<Sample> parent, List<CencSampleAuxiliaryDataFormat> auxiliaryDataFormats) {
        this(new RangeStartMap(Integer.valueOf(0), defaultCek), parent, auxiliaryDataFormats, C0907C.CENC_TYPE_cenc);
    }

    public CencEncryptingSampleList(RangeStartMap<Integer, SecretKey> ceks, List<Sample> parent, List<CencSampleAuxiliaryDataFormat> auxiliaryDataFormats, String encryptionAlgo) {
        this.ceks = new RangeStartMap();
        this.auxiliaryDataFormats = auxiliaryDataFormats;
        this.ceks = ceks;
        this.encryptionAlgo = encryptionAlgo;
        this.parent = parent;
        try {
            if (C0907C.CENC_TYPE_cenc.equals(encryptionAlgo)) {
                this.cipher = Cipher.getInstance("AES/CTR/NoPadding");
            } else if (C0907C.CENC_TYPE_cbc1.equals(encryptionAlgo)) {
                this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
            } else {
                throw new RuntimeException("Only cenc & cbc1 is supported as encryptionAlgo");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public Sample get(int index) {
        Sample clearSample = (Sample) this.parent.get(index);
        if (this.ceks.get(Integer.valueOf(index)) != null) {
            return new EncryptedSampleImpl(clearSample, (CencSampleAuxiliaryDataFormat) this.auxiliaryDataFormats.get(index), this.cipher, (SecretKey) this.ceks.get(Integer.valueOf(index)));
        }
        return clearSample;
    }

    protected void initCipher(byte[] iv, SecretKey cek) {
        try {
            byte[] fullIv = new byte[16];
            System.arraycopy(iv, 0, fullIv, 0, iv.length);
            this.cipher.init(1, cek, new IvParameterSpec(fullIv));
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e2) {
            throw new RuntimeException(e2);
        }
    }

    public int size() {
        return this.parent.size();
    }
}
