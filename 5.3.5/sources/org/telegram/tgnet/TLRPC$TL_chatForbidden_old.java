package org.telegram.tgnet;

public class TLRPC$TL_chatForbidden_old extends TLRPC$TL_chatForbidden {
    public static int constructor = -83047359;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.title = stream.readString(exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.title);
        stream.writeInt32(this.date);
    }
}
