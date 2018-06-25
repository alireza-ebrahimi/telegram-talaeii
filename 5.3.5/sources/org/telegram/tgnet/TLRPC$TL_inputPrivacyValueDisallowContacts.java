package org.telegram.tgnet;

public class TLRPC$TL_inputPrivacyValueDisallowContacts extends TLRPC$InputPrivacyRule {
    public static int constructor = 195371015;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
