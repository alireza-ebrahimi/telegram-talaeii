package org.telegram.tgnet;

public class TLRPC$TL_messages_deleteHistory extends TLObject {
    public static int constructor = 469850889;
    public int flags;
    public boolean just_clear;
    public int max_id;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_affectedHistory.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.just_clear ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.max_id);
    }
}
