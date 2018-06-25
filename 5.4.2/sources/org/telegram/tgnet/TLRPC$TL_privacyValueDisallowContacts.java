package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PrivacyRule;

public class TLRPC$TL_privacyValueDisallowContacts extends PrivacyRule {
    public static int constructor = -125240806;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
