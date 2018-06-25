package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PrivacyRule;

public class TLRPC$TL_privacyValueDisallowAll extends PrivacyRule {
    public static int constructor = -1955338397;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
