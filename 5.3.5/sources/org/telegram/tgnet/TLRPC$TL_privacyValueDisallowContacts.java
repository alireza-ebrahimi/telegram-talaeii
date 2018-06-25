package org.telegram.tgnet;

public class TLRPC$TL_privacyValueDisallowContacts extends TLRPC$PrivacyRule {
    public static int constructor = -125240806;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
