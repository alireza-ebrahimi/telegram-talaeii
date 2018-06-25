package org.telegram.tgnet;

public class TLRPC$TL_messages_search extends TLObject {
    public static int constructor = 60726944;
    public int add_offset;
    public TLRPC$MessagesFilter filter;
    public int flags;
    public TLRPC$InputUser from_id;
    public int limit;
    public int max_date;
    public int max_id;
    public int min_date;
    public int min_id;
    public int offset_id;
    public TLRPC$InputPeer peer;
    /* renamed from: q */
    public String f87q;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Messages.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        stream.writeString(this.f87q);
        if ((this.flags & 1) != 0) {
            this.from_id.serializeToStream(stream);
        }
        this.filter.serializeToStream(stream);
        stream.writeInt32(this.min_date);
        stream.writeInt32(this.max_date);
        stream.writeInt32(this.offset_id);
        stream.writeInt32(this.add_offset);
        stream.writeInt32(this.limit);
        stream.writeInt32(this.max_id);
        stream.writeInt32(this.min_id);
    }
}
