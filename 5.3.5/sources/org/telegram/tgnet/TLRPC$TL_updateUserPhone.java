package org.telegram.tgnet;

public class TLRPC$TL_updateUserPhone extends TLRPC$Update {
    public static int constructor = 314130811;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.phone = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeString(this.phone);
    }
}
