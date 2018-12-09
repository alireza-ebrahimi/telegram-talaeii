package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedMessage;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$updates_Difference extends TLObject {
    public ArrayList<Chat> chats = new ArrayList();
    public int date;
    public TLRPC$TL_updates_state intermediate_state;
    public ArrayList<EncryptedMessage> new_encrypted_messages = new ArrayList();
    public ArrayList<Message> new_messages = new ArrayList();
    public ArrayList<TLRPC$Update> other_updates = new ArrayList();
    public int pts;
    public int seq;
    public TLRPC$TL_updates_state state;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$updates_Difference TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$updates_Difference tLRPC$updates_Difference = null;
        switch (i) {
            case -1459938943:
                tLRPC$updates_Difference = new TLRPC$TL_updates_differenceSlice();
                break;
            case 16030880:
                tLRPC$updates_Difference = new TLRPC$TL_updates_difference();
                break;
            case 1258196845:
                tLRPC$updates_Difference = new TLRPC$TL_updates_differenceTooLong();
                break;
            case 1567990072:
                tLRPC$updates_Difference = new TLRPC$TL_updates_differenceEmpty();
                break;
        }
        if (tLRPC$updates_Difference == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in updates_Difference", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$updates_Difference != null) {
            tLRPC$updates_Difference.readParams(abstractSerializedData, z);
        }
        return tLRPC$updates_Difference;
    }
}
