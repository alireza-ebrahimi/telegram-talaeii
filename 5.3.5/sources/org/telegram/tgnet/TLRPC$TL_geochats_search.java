package org.telegram.tgnet;

public class TLRPC$TL_geochats_search extends TLObject {
    public static int constructor = -808598451;
    public TLRPC$MessagesFilter filter;
    public int limit;
    public int max_date;
    public int max_id;
    public int min_date;
    public int offset;
    public TLRPC$TL_inputGeoChat peer;
    /* renamed from: q */
    public String f81q;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$geochats_Messages.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeString(this.f81q);
        this.filter.serializeToStream(stream);
        stream.writeInt32(this.min_date);
        stream.writeInt32(this.max_date);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.max_id);
        stream.writeInt32(this.limit);
    }
}
