package org.telegram.tgnet;

public class TLRPC$TL_messages_readHistory extends TLObject {
    public static int constructor = 238054714;
    public int max_id;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_affectedMessages.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.max_id);
    }
}
