package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Document;

public abstract class TLRPC$messages_SavedGifs extends TLObject {
    public ArrayList<Document> gifs = new ArrayList();
    public int hash;

    public static TLRPC$messages_SavedGifs TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_SavedGifs tLRPC$messages_SavedGifs = null;
        switch (i) {
            case -402498398:
                tLRPC$messages_SavedGifs = new TLRPC$TL_messages_savedGifsNotModified();
                break;
            case 772213157:
                tLRPC$messages_SavedGifs = new TLRPC$TL_messages_savedGifs();
                break;
        }
        if (tLRPC$messages_SavedGifs == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_SavedGifs", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_SavedGifs != null) {
            tLRPC$messages_SavedGifs.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_SavedGifs;
    }
}
