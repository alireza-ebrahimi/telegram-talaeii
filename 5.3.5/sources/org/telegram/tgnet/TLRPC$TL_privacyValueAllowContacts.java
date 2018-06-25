package org.telegram.tgnet;

public class TLRPC$TL_privacyValueAllowContacts extends TLRPC$PrivacyRule {
    public static int constructor = -123988;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
