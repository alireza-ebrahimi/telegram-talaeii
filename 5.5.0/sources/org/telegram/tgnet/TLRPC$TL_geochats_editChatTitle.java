package org.telegram.tgnet;

public class TLRPC$TL_geochats_editChatTitle extends TLObject {
    public static int constructor = 1284383347;
    public String address;
    public TLRPC$TL_inputGeoChat peer;
    public String title;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.address);
    }
}
