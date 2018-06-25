package org.telegram.messenger;

import org.telegram.tgnet.SerializedData;

public class MessageKeyData {
    public byte[] aesIv;
    public byte[] aesKey;

    public static MessageKeyData generateMessageKeyData(byte[] authKey, byte[] messageKey, boolean incoming, int version) {
        MessageKeyData keyData = new MessageKeyData();
        if (authKey != null && authKey.length != 0) {
            int x = incoming ? 8 : 0;
            SerializedData data;
            switch (version) {
                case 1:
                    data = new SerializedData();
                    data.writeBytes(messageKey);
                    data.writeBytes(authKey, x, 32);
                    byte[] sha1_a = Utilities.computeSHA1(data.toByteArray());
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(authKey, x + 32, 16);
                    data.writeBytes(messageKey);
                    data.writeBytes(authKey, x + 48, 16);
                    byte[] sha1_b = Utilities.computeSHA1(data.toByteArray());
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(authKey, x + 64, 32);
                    data.writeBytes(messageKey);
                    byte[] sha1_c = Utilities.computeSHA1(data.toByteArray());
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(messageKey);
                    data.writeBytes(authKey, x + 96, 32);
                    byte[] sha1_d = Utilities.computeSHA1(data.toByteArray());
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(sha1_a, 0, 8);
                    data.writeBytes(sha1_b, 8, 12);
                    data.writeBytes(sha1_c, 4, 12);
                    keyData.aesKey = data.toByteArray();
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(sha1_a, 8, 12);
                    data.writeBytes(sha1_b, 0, 8);
                    data.writeBytes(sha1_c, 16, 4);
                    data.writeBytes(sha1_d, 0, 8);
                    keyData.aesIv = data.toByteArray();
                    data.cleanup();
                    break;
                case 2:
                    data = new SerializedData();
                    data.writeBytes(messageKey, 0, 16);
                    data.writeBytes(authKey, x, 36);
                    byte[] sha256_a = Utilities.computeSHA256(data.toByteArray());
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(authKey, x + 40, 36);
                    data.writeBytes(messageKey, 0, 16);
                    byte[] sha256_b = Utilities.computeSHA256(data.toByteArray());
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(sha256_a, 0, 8);
                    data.writeBytes(sha256_b, 8, 16);
                    data.writeBytes(sha256_a, 24, 8);
                    keyData.aesKey = data.toByteArray();
                    data.cleanup();
                    data = new SerializedData();
                    data.writeBytes(sha256_b, 0, 8);
                    data.writeBytes(sha256_a, 8, 16);
                    data.writeBytes(sha256_b, 24, 8);
                    keyData.aesIv = data.toByteArray();
                    data.cleanup();
                    break;
                default:
                    break;
            }
        }
        keyData.aesIv = null;
        keyData.aesKey = null;
        return keyData;
    }
}
