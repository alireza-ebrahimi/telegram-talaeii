package org.telegram.tgnet;

public class TLRPC$TL_privacyValueDisallowAll extends TLRPC$PrivacyRule {
    public static int constructor = -1955338397;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
