package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PrivacyRule;

public class TLRPC$TL_privacyValueAllowContacts extends PrivacyRule {
    public static int constructor = -123988;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
