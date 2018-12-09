package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputStickeredMedia;
import org.telegram.tgnet.TLRPC.StickerSetCovered;

public class TLRPC$TL_messages_getAttachedStickers extends TLObject {
    public static int constructor = -866424884;
    public InputStickeredMedia media;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLObject tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            StickerSetCovered TLdeserialize = StickerSetCovered.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                break;
            }
            tLRPC$Vector.objects.add(TLdeserialize);
        }
        return tLRPC$Vector;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.media.serializeToStream(abstractSerializedData);
    }
}
