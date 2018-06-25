package org.telegram.tgnet;

public class TLRPC$TL_messageActionLoginUnknownLocation extends TLRPC$MessageAction {
    public static int constructor = 1431655925;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.title = stream.readString(exception);
        this.address = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.title);
        stream.writeString(this.address);
    }
}
