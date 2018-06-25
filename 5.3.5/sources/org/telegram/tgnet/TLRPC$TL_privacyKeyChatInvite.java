package org.telegram.tgnet;

public class TLRPC$TL_privacyKeyChatInvite extends TLRPC$PrivacyKey {
    public static int constructor = 1343122938;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
