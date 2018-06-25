package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_SavedGifs extends TLObject {
    public ArrayList<TLRPC$Document> gifs = new ArrayList();
    public int hash;

    public static TLRPC$messages_SavedGifs TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_SavedGifs result = null;
        switch (constructor) {
            case -402498398:
                result = new TLRPC$TL_messages_savedGifsNotModified();
                break;
            case 772213157:
                result = new TLRPC$TL_messages_savedGifs();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_SavedGifs", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
