package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_FavedStickers extends TLObject {
    public int hash;
    public ArrayList<TLRPC$TL_stickerPack> packs = new ArrayList();
    public ArrayList<TLRPC$Document> stickers = new ArrayList();

    public static TLRPC$messages_FavedStickers TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_FavedStickers result = null;
        switch (constructor) {
            case -1634752813:
                result = new TLRPC$TL_messages_favedStickersNotModified();
                break;
            case -209768682:
                result = new TLRPC$TL_messages_favedStickers();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_FavedStickers", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
