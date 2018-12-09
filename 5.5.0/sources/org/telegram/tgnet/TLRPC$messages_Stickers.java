package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Document;

public abstract class TLRPC$messages_Stickers extends TLObject {
    public String hash;
    public ArrayList<Document> stickers = new ArrayList();

    public static TLRPC$messages_Stickers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_Stickers tLRPC$messages_Stickers = null;
        switch (i) {
            case -1970352846:
                tLRPC$messages_Stickers = new TLRPC$TL_messages_stickers();
                break;
            case -244016606:
                tLRPC$messages_Stickers = new TLRPC$TL_messages_stickersNotModified();
                break;
        }
        if (tLRPC$messages_Stickers == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Stickers", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_Stickers != null) {
            tLRPC$messages_Stickers.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_Stickers;
    }
}
