package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$messages_Dialogs extends TLObject {
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int count;
    public ArrayList<TLRPC$TL_dialog> dialogs = new ArrayList();
    public ArrayList<TLRPC$Message> messages = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$messages_Dialogs TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_Dialogs result = null;
        switch (constructor) {
            case 364538944:
                result = new TLRPC$TL_messages_dialogs();
                break;
            case 1910543603:
                result = new TLRPC$TL_messages_dialogsSlice();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Dialogs", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
