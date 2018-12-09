package org.telegram.tgnet;

public class TLRPC$TL_geochats_getHistory extends TLObject {
    public static int constructor = -1254131096;
    public int limit;
    public int max_id;
    public int offset;
    public TLRPC$TL_inputGeoChat peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$geochats_Messages.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.max_id);
        abstractSerializedData.writeInt32(this.limit);
    }
}
