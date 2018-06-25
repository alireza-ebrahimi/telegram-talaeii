package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$messages_Messages extends TLObject {
    public ArrayList<Chat> chats = new ArrayList();
    public int count;
    public int flags;
    public ArrayList<Message> messages = new ArrayList();
    public int pts;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$messages_Messages TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_Messages tLRPC$messages_Messages = null;
        switch (i) {
            case -1938715001:
                tLRPC$messages_Messages = new TLRPC$TL_messages_messages();
                break;
            case -1725551049:
                tLRPC$messages_Messages = new TLRPC$TL_messages_channelMessages();
                break;
            case 189033187:
                tLRPC$messages_Messages = new TLRPC$TL_messages_messagesSlice();
                break;
        }
        if (tLRPC$messages_Messages == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_Messages", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_Messages != null) {
            tLRPC$messages_Messages.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_Messages;
    }
}
