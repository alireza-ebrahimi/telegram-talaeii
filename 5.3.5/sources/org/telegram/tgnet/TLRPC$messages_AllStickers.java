package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_AllStickers extends TLObject {
    public ArrayList<TLRPC$Document> documents = new ArrayList();
    public String hash;
    public ArrayList<TLRPC$TL_stickerPack> packs = new ArrayList();
    public ArrayList<TLRPC$StickerSet> sets = new ArrayList();

    public static TLRPC$messages_AllStickers TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_AllStickers result = null;
        switch (constructor) {
            case -395967805:
                result = new TLRPC$TL_messages_allStickersNotModified();
                break;
            case -302170017:
                result = new TLRPC$TL_messages_allStickers();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_AllStickers", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
