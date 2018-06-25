package org.telegram.tgnet;

public class TLRPC$TL_messages_getRecentStickers extends TLObject {
    public static int constructor = 1587647177;
    public boolean attached;
    public int flags;
    public int hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_RecentStickers.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.attached ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.hash);
    }
}
