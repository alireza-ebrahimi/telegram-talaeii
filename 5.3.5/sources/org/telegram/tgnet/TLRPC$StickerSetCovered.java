package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$StickerSetCovered extends TLObject {
    public TLRPC$Document cover;
    public ArrayList<TLRPC$Document> covers = new ArrayList();
    public TLRPC$StickerSet set;

    public static TLRPC$StickerSetCovered TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$StickerSetCovered result = null;
        switch (constructor) {
            case 872932635:
                result = new TLRPC$TL_stickerSetMultiCovered();
                break;
            case 1678812626:
                result = new TLRPC$TL_stickerSetCovered();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in StickerSetCovered", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
