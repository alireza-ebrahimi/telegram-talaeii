package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.StickerSetCovered;

public abstract class TLRPC$messages_FeaturedStickers extends TLObject {
    public int hash;
    public ArrayList<StickerSetCovered> sets = new ArrayList();
    public ArrayList<Long> unread = new ArrayList();

    public static TLRPC$messages_FeaturedStickers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_FeaturedStickers tLRPC$messages_FeaturedStickers = null;
        switch (i) {
            case -123893531:
                tLRPC$messages_FeaturedStickers = new TLRPC$TL_messages_featuredStickers();
                break;
            case 82699215:
                tLRPC$messages_FeaturedStickers = new TLRPC$TL_messages_featuredStickersNotModified();
                break;
        }
        if (tLRPC$messages_FeaturedStickers == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_FeaturedStickers", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_FeaturedStickers != null) {
            tLRPC$messages_FeaturedStickers.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_FeaturedStickers;
    }
}
