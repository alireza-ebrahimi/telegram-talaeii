package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyKey;

public class TLRPC$TL_inputPrivacyKeyPhoneCall extends InputPrivacyKey {
    public static int constructor = -88417185;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
