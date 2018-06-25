package org.telegram.messenger;

import org.telegram.tgnet.SerializedData;

public class MessageKeyData {
    public byte[] aesIv;
    public byte[] aesKey;

    public static MessageKeyData generateMessageKeyData(byte[] bArr, byte[] bArr2, boolean z, int i) {
        MessageKeyData messageKeyData = new MessageKeyData();
        if (bArr != null && bArr.length != 0) {
            int i2 = z ? 8 : 0;
            SerializedData serializedData;
            byte[] computeSHA1;
            byte[] computeSHA12;
            switch (i) {
                case 1:
                    serializedData = new SerializedData();
                    serializedData.writeBytes(bArr2);
                    serializedData.writeBytes(bArr, i2, 32);
                    computeSHA1 = Utilities.computeSHA1(serializedData.toByteArray());
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(bArr, i2 + 32, 16);
                    serializedData.writeBytes(bArr2);
                    serializedData.writeBytes(bArr, i2 + 48, 16);
                    byte[] computeSHA13 = Utilities.computeSHA1(serializedData.toByteArray());
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(bArr, i2 + 64, 32);
                    serializedData.writeBytes(bArr2);
                    byte[] computeSHA14 = Utilities.computeSHA1(serializedData.toByteArray());
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(bArr2);
                    serializedData.writeBytes(bArr, i2 + 96, 32);
                    computeSHA12 = Utilities.computeSHA1(serializedData.toByteArray());
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(computeSHA1, 0, 8);
                    serializedData.writeBytes(computeSHA13, 8, 12);
                    serializedData.writeBytes(computeSHA14, 4, 12);
                    messageKeyData.aesKey = serializedData.toByteArray();
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(computeSHA1, 8, 12);
                    serializedData.writeBytes(computeSHA13, 0, 8);
                    serializedData.writeBytes(computeSHA14, 16, 4);
                    serializedData.writeBytes(computeSHA12, 0, 8);
                    messageKeyData.aesIv = serializedData.toByteArray();
                    serializedData.cleanup();
                    break;
                case 2:
                    serializedData = new SerializedData();
                    serializedData.writeBytes(bArr2, 0, 16);
                    serializedData.writeBytes(bArr, i2, 36);
                    computeSHA1 = Utilities.computeSHA256(serializedData.toByteArray());
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(bArr, i2 + 40, 36);
                    serializedData.writeBytes(bArr2, 0, 16);
                    computeSHA12 = Utilities.computeSHA256(serializedData.toByteArray());
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(computeSHA1, 0, 8);
                    serializedData.writeBytes(computeSHA12, 8, 16);
                    serializedData.writeBytes(computeSHA1, 24, 8);
                    messageKeyData.aesKey = serializedData.toByteArray();
                    serializedData.cleanup();
                    serializedData = new SerializedData();
                    serializedData.writeBytes(computeSHA12, 0, 8);
                    serializedData.writeBytes(computeSHA1, 8, 16);
                    serializedData.writeBytes(computeSHA12, 24, 8);
                    messageKeyData.aesIv = serializedData.toByteArray();
                    serializedData.cleanup();
                    break;
                default:
                    break;
            }
        }
        messageKeyData.aesIv = null;
        messageKeyData.aesKey = null;
        return messageKeyData;
    }
}
