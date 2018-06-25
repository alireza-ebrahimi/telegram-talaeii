package org.telegram.tgnet;

public class TLRPC$TL_userStatusLastMonth extends TLRPC$UserStatus {
    public static int constructor = 2011940674;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
