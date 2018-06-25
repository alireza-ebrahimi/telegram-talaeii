package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;

public abstract class TLRPC$messages_Chats extends TLObject {
    public ArrayList<Chat> chats = new ArrayList();
    public int count;

    public static TLRPC$messages_Chats TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_Chats tLRPC$messages_Chats = null;
        switch (i) {
            case -1663561404:
                tLRPC$messages_Chats = new TLRPC$TL_messages_chatsSlice();
                break;
            case 1694474197:
                tLRPC$messages_Chats = new TLRPC$TL_messages_chats();
                break;
        }
        if (tLRPC$messages_Chats == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Chats", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_Chats != null) {
            tLRPC$messages_Chats.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_Chats;
    }
}
