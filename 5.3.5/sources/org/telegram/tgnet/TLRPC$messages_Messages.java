package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$messages_Messages extends TLObject {
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int count;
    public int flags;
    public ArrayList<TLRPC$Message> messages = new ArrayList();
    public int pts;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$messages_Messages TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_Messages result = null;
        switch (constructor) {
            case -1938715001:
                result = new TLRPC$TL_messages_messages();
                break;
            case -1725551049:
                result = new TLRPC$TL_messages_channelMessages();
                break;
            case 189033187:
                result = new TLRPC$TL_messages_messagesSlice();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Messages", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
