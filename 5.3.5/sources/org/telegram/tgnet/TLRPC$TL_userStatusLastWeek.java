package org.telegram.tgnet;

public class TLRPC$TL_userStatusLastWeek extends TLRPC$UserStatus {
    public static int constructor = 129960444;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
