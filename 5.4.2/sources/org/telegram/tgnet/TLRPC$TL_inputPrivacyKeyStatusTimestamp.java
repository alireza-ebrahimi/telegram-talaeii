package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyKey;

public class TLRPC$TL_inputPrivacyKeyStatusTimestamp extends InputPrivacyKey {
    public static int constructor = 1335282456;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
