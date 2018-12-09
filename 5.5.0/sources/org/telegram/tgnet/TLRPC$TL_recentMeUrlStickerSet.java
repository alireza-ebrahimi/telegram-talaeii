package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RecentMeUrl;
import org.telegram.tgnet.TLRPC.StickerSetCovered;

public class TLRPC$TL_recentMeUrlStickerSet extends RecentMeUrl {
    public static int constructor = -1140172836;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
        this.set = StickerSetCovered.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        this.set.serializeToStream(abstractSerializedData);
    }
}
