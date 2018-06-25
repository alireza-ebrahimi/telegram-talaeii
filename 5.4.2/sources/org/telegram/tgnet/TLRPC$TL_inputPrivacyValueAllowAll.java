package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyRule;

public class TLRPC$TL_inputPrivacyValueAllowAll extends InputPrivacyRule {
    public static int constructor = 407582158;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
