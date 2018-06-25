package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyRule;

public class TLRPC$TL_inputPrivacyValueDisallowAll extends InputPrivacyRule {
    public static int constructor = -697604407;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
