package org.telegram.tgnet;

public class TLRPC$TL_geoChatMessageEmpty extends TLRPC$GeoChatMessage {
    public static int constructor = 1613830811;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.id);
    }
}
