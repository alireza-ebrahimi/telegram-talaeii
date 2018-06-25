package org.telegram.tgnet;

public class TLRPC$TL_geoChatMessageService extends TLRPC$GeoChatMessage {
    public static int constructor = -749755826;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.id = stream.readInt32(exception);
        this.from_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.action = TLRPC$MessageAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.id);
        stream.writeInt32(this.from_id);
        stream.writeInt32(this.date);
        this.action.serializeToStream(stream);
    }
}
