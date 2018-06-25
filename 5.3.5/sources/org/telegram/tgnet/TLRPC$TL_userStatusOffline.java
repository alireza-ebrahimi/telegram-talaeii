package org.telegram.tgnet;

public class TLRPC$TL_userStatusOffline extends TLRPC$UserStatus {
    public static int constructor = 9203775;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.expires = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.expires);
    }
}
