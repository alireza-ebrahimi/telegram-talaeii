package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.MessagesFilter;

public class TLRPC$TL_messages_search extends TLObject {
    public static int constructor = 60726944;
    public int add_offset;
    public MessagesFilter filter;
    public int flags;
    public InputUser from_id;
    public int limit;
    public int max_date;
    public int max_id;
    public int min_date;
    public int min_id;
    public int offset_id;
    public InputPeer peer;
    /* renamed from: q */
    public String f10166q;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_Messages.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.f10166q);
        if ((this.flags & 1) != 0) {
            this.from_id.serializeToStream(abstractSerializedData);
        }
        this.filter.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.min_date);
        abstractSerializedData.writeInt32(this.max_date);
        abstractSerializedData.writeInt32(this.offset_id);
        abstractSerializedData.writeInt32(this.add_offset);
        abstractSerializedData.writeInt32(this.limit);
        abstractSerializedData.writeInt32(this.max_id);
        abstractSerializedData.writeInt32(this.min_id);
    }
}
