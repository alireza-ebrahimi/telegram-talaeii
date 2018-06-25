package org.telegram.tgnet;

public class TLRPC$TL_contacts_search extends TLObject {
    public static int constructor = 301470424;
    public int limit;
    /* renamed from: q */
    public String f80q;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_contacts_found.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.f80q);
        stream.writeInt32(this.limit);
    }
}
