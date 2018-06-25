package org.telegram.tgnet;

public class TLRPC$TL_messages_getMessageEditData extends TLObject {
    public static int constructor = -39416522;
    public int id;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_messageEditData.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.id);
    }
}
