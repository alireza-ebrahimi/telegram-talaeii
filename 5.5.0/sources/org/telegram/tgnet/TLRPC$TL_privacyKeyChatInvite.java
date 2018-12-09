package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PrivacyKey;

public class TLRPC$TL_privacyKeyChatInvite extends PrivacyKey {
    public static int constructor = 1343122938;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
