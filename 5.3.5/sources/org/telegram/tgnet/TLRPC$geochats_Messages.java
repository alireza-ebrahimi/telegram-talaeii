package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$geochats_Messages extends TLObject {
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int count;
    public ArrayList<TLRPC$GeoChatMessage> messages = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$geochats_Messages TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$geochats_Messages result = null;
        switch (constructor) {
            case -1135057944:
                result = new TLRPC$TL_geochats_messagesSlice();
                break;
            case -783127119:
                result = new TLRPC$TL_geochats_messages();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in geochats_Messages", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
