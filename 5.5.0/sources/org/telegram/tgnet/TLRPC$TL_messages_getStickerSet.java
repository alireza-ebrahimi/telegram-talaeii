package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputStickerSet;

public class TLRPC$TL_messages_getStickerSet extends TLObject {
    public static int constructor = 639215886;
    public InputStickerSet stickerset;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_stickerSet.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.stickerset.serializeToStream(abstractSerializedData);
    }
}
