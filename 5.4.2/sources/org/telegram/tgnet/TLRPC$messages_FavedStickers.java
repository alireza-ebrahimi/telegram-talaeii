package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Document;

public abstract class TLRPC$messages_FavedStickers extends TLObject {
    public int hash;
    public ArrayList<TLRPC$TL_stickerPack> packs = new ArrayList();
    public ArrayList<Document> stickers = new ArrayList();

    public static TLRPC$messages_FavedStickers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_FavedStickers tLRPC$messages_FavedStickers = null;
        switch (i) {
            case -1634752813:
                tLRPC$messages_FavedStickers = new TLRPC$TL_messages_favedStickersNotModified();
                break;
            case -209768682:
                tLRPC$messages_FavedStickers = new TLRPC$TL_messages_favedStickers();
                break;
        }
        if (tLRPC$messages_FavedStickers == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_FavedStickers", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_FavedStickers != null) {
            tLRPC$messages_FavedStickers.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_FavedStickers;
    }
}
