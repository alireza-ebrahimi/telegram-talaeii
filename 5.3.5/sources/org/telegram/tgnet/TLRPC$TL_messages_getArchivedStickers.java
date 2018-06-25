package org.telegram.tgnet;

public class TLRPC$TL_messages_getArchivedStickers extends TLObject {
    public static int constructor = 1475442322;
    public int flags;
    public int limit;
    public boolean masks;
    public long offset_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_archivedStickers.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.masks ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt64(this.offset_id);
        stream.writeInt32(this.limit);
    }
}
