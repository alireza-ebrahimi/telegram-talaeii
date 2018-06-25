package org.telegram.tgnet;

public class TLRPC$TL_messages_getDialogs extends TLObject {
    public static int constructor = 421243333;
    public boolean exclude_pinned;
    public int flags;
    public int limit;
    public int offset_date;
    public int offset_id;
    public TLRPC$InputPeer offset_peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Dialogs.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.exclude_pinned ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.offset_date);
        stream.writeInt32(this.offset_id);
        this.offset_peer.serializeToStream(stream);
        stream.writeInt32(this.limit);
    }
}
