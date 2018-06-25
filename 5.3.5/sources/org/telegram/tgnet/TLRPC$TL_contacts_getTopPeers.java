package org.telegram.tgnet;

public class TLRPC$TL_contacts_getTopPeers extends TLObject {
    public static int constructor = -728224331;
    public boolean bots_inline;
    public boolean bots_pm;
    public boolean channels;
    public boolean correspondents;
    public int flags;
    public boolean groups;
    public int hash;
    public int limit;
    public int offset;
    public boolean phone_calls;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$contacts_TopPeers.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.correspondents ? this.flags | 1 : this.flags & -2;
        this.flags = this.bots_pm ? this.flags | 2 : this.flags & -3;
        this.flags = this.bots_inline ? this.flags | 4 : this.flags & -5;
        this.flags = this.phone_calls ? this.flags | 8 : this.flags & -9;
        this.flags = this.groups ? this.flags | 1024 : this.flags & -1025;
        this.flags = this.channels ? this.flags | 32768 : this.flags & -32769;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.limit);
        stream.writeInt32(this.hash);
    }
}
