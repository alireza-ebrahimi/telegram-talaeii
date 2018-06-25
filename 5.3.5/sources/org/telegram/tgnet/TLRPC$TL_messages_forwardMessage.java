package org.telegram.tgnet;

public class TLRPC$TL_messages_forwardMessage extends TLObject {
    public static int constructor = 865483769;
    public int id;
    public TLRPC$InputPeer peer;
    public long random_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.id);
        stream.writeInt64(this.random_id);
    }
}
