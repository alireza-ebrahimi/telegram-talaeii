package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyRule;

public class TLRPC$TL_inputPrivacyValueDisallowContacts extends InputPrivacyRule {
    public static int constructor = 195371015;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
