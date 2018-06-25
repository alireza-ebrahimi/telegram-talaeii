package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_Chats extends TLObject {
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int count;

    public static TLRPC$messages_Chats TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_Chats result = null;
        switch (constructor) {
            case -1663561404:
                result = new TLRPC$TL_messages_chatsSlice();
                break;
            case 1694474197:
                result = new TLRPC$TL_messages_chats();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Chats", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
