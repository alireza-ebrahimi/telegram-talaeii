package org.telegram.tgnet;

public class TLRPC$TL_messages_searchGifs extends TLObject {
    public static int constructor = -1080395925;
    public int offset;
    /* renamed from: q */
    public String f10167q;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_foundGifs.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.f10167q);
        abstractSerializedData.writeInt32(this.offset);
    }
}
