package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChatPhoto;

public class TLRPC$TL_geochats_editChatPhoto extends TLObject {
    public static int constructor = 903355029;
    public TLRPC$TL_inputGeoChat peer;
    public InputChatPhoto photo;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.photo.serializeToStream(abstractSerializedData);
    }
}
