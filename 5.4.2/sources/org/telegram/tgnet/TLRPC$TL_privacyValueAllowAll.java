package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PrivacyRule;

public class TLRPC$TL_privacyValueAllowAll extends PrivacyRule {
    public static int constructor = 1698855810;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
