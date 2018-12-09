package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.GeoChatMessage;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$geochats_Messages extends TLObject {
    public ArrayList<Chat> chats = new ArrayList();
    public int count;
    public ArrayList<GeoChatMessage> messages = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$geochats_Messages TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$geochats_Messages tLRPC$geochats_Messages = null;
        switch (i) {
            case -1135057944:
                tLRPC$geochats_Messages = new TLRPC$TL_geochats_messagesSlice();
                break;
            case -783127119:
                tLRPC$geochats_Messages = new TLRPC$TL_geochats_messages();
                break;
        }
        if (tLRPC$geochats_Messages == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in geochats_Messages", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$geochats_Messages != null) {
            tLRPC$geochats_Messages.readParams(abstractSerializedData, z);
        }
        return tLRPC$geochats_Messages;
    }
}
