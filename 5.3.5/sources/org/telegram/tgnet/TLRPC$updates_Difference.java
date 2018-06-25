package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$updates_Difference extends TLObject {
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int date;
    public TLRPC$TL_updates_state intermediate_state;
    public ArrayList<TLRPC$EncryptedMessage> new_encrypted_messages = new ArrayList();
    public ArrayList<TLRPC$Message> new_messages = new ArrayList();
    public ArrayList<TLRPC$Update> other_updates = new ArrayList();
    public int pts;
    public int seq;
    public TLRPC$TL_updates_state state;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$updates_Difference TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$updates_Difference result = null;
        switch (constructor) {
            case -1459938943:
                result = new TLRPC$TL_updates_differenceSlice();
                break;
            case 16030880:
                result = new TLRPC$TL_updates_difference();
                break;
            case 1258196845:
                result = new TLRPC$TL_updates_differenceTooLong();
                break;
            case 1567990072:
                result = new TLRPC$TL_updates_differenceEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in updates_Difference", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
