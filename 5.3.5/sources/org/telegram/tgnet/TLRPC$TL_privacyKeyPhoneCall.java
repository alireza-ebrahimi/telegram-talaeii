package org.telegram.tgnet;

public class TLRPC$TL_privacyKeyPhoneCall extends TLRPC$PrivacyKey {
    public static int constructor = 1030105979;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
