package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessagesFilter;

public class TLRPC$TL_geochats_search extends TLObject {
    public static int constructor = -808598451;
    public MessagesFilter filter;
    public int limit;
    public int max_date;
    public int max_id;
    public int min_date;
    public int offset;
    public TLRPC$TL_inputGeoChat peer;
    /* renamed from: q */
    public String f10160q;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$geochats_Messages.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.f10160q);
        this.filter.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.min_date);
        abstractSerializedData.writeInt32(this.max_date);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.max_id);
        abstractSerializedData.writeInt32(this.limit);
    }
}
