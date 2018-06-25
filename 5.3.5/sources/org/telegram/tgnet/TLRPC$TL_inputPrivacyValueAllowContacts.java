package org.telegram.tgnet;

public class TLRPC$TL_inputPrivacyValueAllowContacts extends TLRPC$InputPrivacyRule {
    public static int constructor = 218751099;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
