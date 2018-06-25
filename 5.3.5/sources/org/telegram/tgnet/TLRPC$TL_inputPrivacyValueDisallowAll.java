package org.telegram.tgnet;

public class TLRPC$TL_inputPrivacyValueDisallowAll extends TLRPC$InputPrivacyRule {
    public static int constructor = -697604407;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
