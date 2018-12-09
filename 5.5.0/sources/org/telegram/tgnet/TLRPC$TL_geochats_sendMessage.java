package org.telegram.tgnet;

public class TLRPC$TL_geochats_sendMessage extends TLObject {
    public static int constructor = 102432836;
    public String message;
    public TLRPC$TL_inputGeoChat peer;
    public long random_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.message);
        abstractSerializedData.writeInt64(this.random_id);
    }
}
