package org.telegram.tgnet;

public class TLRPC$TL_langpack_getDifference extends TLObject {
    public static int constructor = 187583869;
    public int from_version;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_langPackDifference.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.from_version);
    }
}
