package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.StickerSet;

public abstract class TLRPC$messages_AllStickers extends TLObject {
    public ArrayList<Document> documents = new ArrayList();
    public String hash;
    public ArrayList<TLRPC$TL_stickerPack> packs = new ArrayList();
    public ArrayList<StickerSet> sets = new ArrayList();

    public static TLRPC$messages_AllStickers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_AllStickers tLRPC$messages_AllStickers = null;
        switch (i) {
            case -395967805:
                tLRPC$messages_AllStickers = new TLRPC$TL_messages_allStickersNotModified();
                break;
            case -302170017:
                tLRPC$messages_AllStickers = new TLRPC$TL_messages_allStickers();
                break;
        }
        if (tLRPC$messages_AllStickers == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_AllStickers", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_AllStickers != null) {
            tLRPC$messages_AllStickers.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_AllStickers;
    }
}
