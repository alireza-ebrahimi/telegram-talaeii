package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_FeaturedStickers extends TLObject {
    public int hash;
    public ArrayList<TLRPC$StickerSetCovered> sets = new ArrayList();
    public ArrayList<Long> unread = new ArrayList();

    public static TLRPC$messages_FeaturedStickers TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_FeaturedStickers result = null;
        switch (constructor) {
            case -123893531:
                result = new TLRPC$TL_messages_featuredStickers();
                break;
            case 82699215:
                result = new TLRPC$TL_messages_featuredStickersNotModified();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_FeaturedStickers", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
