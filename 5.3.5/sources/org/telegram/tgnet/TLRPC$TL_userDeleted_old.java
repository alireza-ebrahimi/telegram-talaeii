package org.telegram.tgnet;

public class TLRPC$TL_userDeleted_old extends TLRPC$TL_userDeleted_old2 {
    public static int constructor = -1298475060;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.first_name = stream.readString(exception);
        this.last_name = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.first_name);
        stream.writeString(this.last_name);
    }
}
