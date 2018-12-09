package org.telegram.tgnet;

public class TLRPC$TL_langpack_getLangPack extends TLObject {
    public static int constructor = -1699363442;
    public String lang_code;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_langPackDifference.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.lang_code);
    }
}
