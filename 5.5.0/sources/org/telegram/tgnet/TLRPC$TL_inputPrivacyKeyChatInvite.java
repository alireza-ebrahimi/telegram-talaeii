package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPrivacyKey;

public class TLRPC$TL_inputPrivacyKeyChatInvite extends InputPrivacyKey {
    public static int constructor = -1107622874;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
