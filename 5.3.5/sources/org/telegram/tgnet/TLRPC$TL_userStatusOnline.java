package org.telegram.tgnet;

public class TLRPC$TL_userStatusOnline extends TLRPC$UserStatus {
    public static int constructor = -306628279;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.expires = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.expires);
    }
}
