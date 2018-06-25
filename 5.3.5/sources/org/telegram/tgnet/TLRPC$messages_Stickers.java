package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_Stickers extends TLObject {
    public String hash;
    public ArrayList<TLRPC$Document> stickers = new ArrayList();

    public static TLRPC$messages_Stickers TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_Stickers result = null;
        switch (constructor) {
            case -1970352846:
                result = new TLRPC$TL_messages_stickers();
                break;
            case -244016606:
                result = new TLRPC$TL_messages_stickersNotModified();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Stickers", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
