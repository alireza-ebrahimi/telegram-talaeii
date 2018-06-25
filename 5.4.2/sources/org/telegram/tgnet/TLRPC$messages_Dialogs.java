package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$messages_Dialogs extends TLObject {
    public ArrayList<Chat> chats = new ArrayList();
    public int count;
    public ArrayList<TLRPC$TL_dialog> dialogs = new ArrayList();
    public ArrayList<Message> messages = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$messages_Dialogs TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_Dialogs tLRPC$messages_Dialogs = null;
        switch (i) {
            case 364538944:
                tLRPC$messages_Dialogs = new TLRPC$TL_messages_dialogs();
                break;
            case 1910543603:
                tLRPC$messages_Dialogs = new TLRPC$TL_messages_dialogsSlice();
                break;
        }
        if (tLRPC$messages_Dialogs == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Dialogs", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_Dialogs != null) {
            tLRPC$messages_Dialogs.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_Dialogs;
    }
}
