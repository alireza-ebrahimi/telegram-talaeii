package org.telegram.tgnet;

public class TLRPC$TL_privacyKeyStatusTimestamp extends TLRPC$PrivacyKey {
    public static int constructor = -1137792208;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
