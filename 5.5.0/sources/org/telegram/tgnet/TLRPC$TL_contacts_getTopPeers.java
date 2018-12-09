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

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$contacts_TopPeers.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.correspondents ? this.flags | 1 : this.flags & -2;
        this.flags = this.bots_pm ? this.flags | 2 : this.flags & -3;
        this.flags = this.bots_inline ? this.flags | 4 : this.flags & -5;
        this.flags = this.phone_calls ? this.flags | 8 : this.flags & -9;
        this.flags = this.groups ? this.flags | 1024 : this.flags & -1025;
        this.flags = this.channels ? this.flags | TLRPC.MESSAGE_FLAG_EDITED : this.flags & -32769;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.limit);
        abstractSerializedData.writeInt32(this.hash);
    }
}
