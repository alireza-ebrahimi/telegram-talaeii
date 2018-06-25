package com.googlecode.mp4parser.boxes.cenc;

import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.RangeStartMap;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair;
import java.nio.ByteBuffer;
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
import javax.crypto.spec.IvParameterSpec;
import org.telegram.messenger.exoplayer2.C0907C;

public class CencDecryptingSampleList extends AbstractList<Sample> {
    String encryptionAlgo;
    RangeStartMap<Integer, SecretKey> keys;
    List<Sample> parent;
    List<CencSampleAuxiliaryDataFormat> sencInfo;

    public CencDecryptingSampleList(SecretKey secretKey, List<Sample> parent, List<CencSampleAuxiliaryDataFormat> sencInfo) {
        this(new RangeStartMap(Integer.valueOf(0), secretKey), parent, sencInfo, C0907C.CENC_TYPE_cenc);
    }

    public CencDecryptingSampleList(RangeStartMap<Integer, SecretKey> keys, List<Sample> parent, List<CencSampleAuxiliaryDataFormat> sencInfo, String encryptionAlgo) {
        this.keys = new RangeStartMap();
        this.sencInfo = sencInfo;
        this.keys = keys;
        this.parent = parent;
        this.encryptionAlgo = encryptionAlgo;
    }

    Cipher getCipher(SecretKey sk, byte[] iv) {
        byte[] fullIv = new byte[16];
        System.arraycopy(iv, 0, fullIv, 0, iv.length);
        try {
            Cipher c;
            if (C0907C.CENC_TYPE_cenc.equals(this.encryptionAlgo)) {
                c = Cipher.getInstance("AES/CTR/NoPadding");
                c.init(2, sk, new IvParameterSpec(fullIv));
                return c;
            } else if (C0907C.CENC_TYPE_cbc1.equals(this.encryptionAlgo)) {
                c = Cipher.getInstance("AES/CBC/NoPadding");
                c.init(2, sk, new IvParameterSpec(fullIv));
                return c;
            } else {
                throw new RuntimeException("Only cenc & cbc1 is supported as encryptionAlgo");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e2) {
            throw new RuntimeException(e2);
        } catch (InvalidAlgorithmParameterException e3) {
            throw new RuntimeException(e3);
        } catch (InvalidKeyException e4) {
            throw new RuntimeException(e4);
        }
    }

    public Sample get(int index) {
        if (this.keys.get(Integer.valueOf(index)) == null) {
            return (Sample) this.parent.get(index);
        }
        Sample encSample = (Sample) this.parent.get(index);
        ByteBuffer encSampleBuffer = encSample.asByteBuffer();
        encSampleBuffer.rewind();
        ByteBuffer decSampleBuffer = ByteBuffer.allocate(encSampleBuffer.limit());
        CencSampleAuxiliaryDataFormat sencEntry = (CencSampleAuxiliaryDataFormat) this.sencInfo.get(index);
        Cipher cipher = getCipher((SecretKey) this.keys.get(Integer.valueOf(index)), sencEntry.iv);
        try {
            if (sencEntry.pairs == null || sencEntry.pairs.length <= 0) {
                byte[] fullyEncryptedSample = new byte[encSampleBuffer.limit()];
                encSampleBuffer.get(fullyEncryptedSample);
                if (C0907C.CENC_TYPE_cbc1.equals(this.encryptionAlgo)) {
                    int encryptedLength = (fullyEncryptedSample.length / 16) * 16;
                    decSampleBuffer.put(cipher.doFinal(fullyEncryptedSample, 0, encryptedLength));
                    decSampleBuffer.put(fullyEncryptedSample, encryptedLength, fullyEncryptedSample.length - encryptedLength);
                } else if (C0907C.CENC_TYPE_cenc.equals(this.encryptionAlgo)) {
                    decSampleBuffer.put(cipher.doFinal(fullyEncryptedSample));
                }
            } else {
                for (Pair pair : sencEntry.pairs) {
                    int clearBytes = pair.clear();
                    int encrypted = CastUtils.l2i(pair.encrypted());
                    byte[] clears = new byte[clearBytes];
                    encSampleBuffer.get(clears);
                    decSampleBuffer.put(clears);
                    if (encrypted > 0) {
                        byte[] encs = new byte[encrypted];
                        encSampleBuffer.get(encs);
                        decSampleBuffer.put(cipher.update(encs));
                    }
                }
                if (encSampleBuffer.remaining() > 0) {
                    System.err.println("Decrypted sample but still data remaining: " + encSample.getSize());
                }
                decSampleBuffer.put(cipher.doFinal());
            }
            encSampleBuffer.rewind();
            decSampleBuffer.rewind();
            return new SampleImpl(decSampleBuffer);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public int size() {
        return this.parent.size();
    }
}
