package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_geochats_sendMedia extends TLObject {
    public static int constructor = -1192173825;
    public InputMedia media;
    public TLRPC$TL_inputGeoChat peer;
    public long random_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.media.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt64(this.random_id);
    }
}
