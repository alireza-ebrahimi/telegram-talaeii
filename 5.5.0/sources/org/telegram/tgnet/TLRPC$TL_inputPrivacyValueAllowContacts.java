package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyRule;

public class TLRPC$TL_inputPrivacyValueAllowContacts extends InputPrivacyRule {
    public static int constructor = 218751099;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
