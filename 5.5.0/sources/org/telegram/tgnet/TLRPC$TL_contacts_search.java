package org.telegram.tgnet;

public class TLRPC$TL_contacts_search extends TLObject {
    public static int constructor = 301470424;
    public int limit;
    /* renamed from: q */
    public String f10159q;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_contacts_found.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.f10159q);
        abstractSerializedData.writeInt32(this.limit);
    }
}
