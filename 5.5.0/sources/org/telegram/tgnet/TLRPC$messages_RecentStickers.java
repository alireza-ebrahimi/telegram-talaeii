package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Document;

public abstract class TLRPC$messages_RecentStickers extends TLObject {
    public int hash;
    public ArrayList<Document> stickers = new ArrayList();

    public static TLRPC$messages_RecentStickers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_RecentStickers tLRPC$messages_RecentStickers = null;
        switch (i) {
            case 186120336:
                tLRPC$messages_RecentStickers = new TLRPC$TL_messages_recentStickersNotModified();
                break;
            case 1558317424:
                tLRPC$messages_RecentStickers = new TLRPC$TL_messages_recentStickers();
                break;
        }
        if (tLRPC$messages_RecentStickers == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_RecentStickers", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_RecentStickers != null) {
            tLRPC$messages_RecentStickers.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_RecentStickers;
    }
}
