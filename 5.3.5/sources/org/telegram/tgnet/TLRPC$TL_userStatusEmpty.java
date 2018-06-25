package org.telegram.tgnet;

public class TLRPC$TL_userStatusEmpty extends TLRPC$UserStatus {
    public static int constructor = 164646985;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
