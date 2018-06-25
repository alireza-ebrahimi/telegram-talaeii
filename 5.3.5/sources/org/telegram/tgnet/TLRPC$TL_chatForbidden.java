package org.telegram.tgnet;

public class TLRPC$TL_chatForbidden extends TLRPC$Chat {
    public static int constructor = 120753115;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.title = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.title);
    }
}
