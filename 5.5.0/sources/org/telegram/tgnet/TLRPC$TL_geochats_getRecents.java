package org.telegram.tgnet;

public class TLRPC$TL_geochats_getRecents extends TLObject {
    public static int constructor = -515735953;
    public int limit;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$geochats_Messages.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.limit);
    }
}
