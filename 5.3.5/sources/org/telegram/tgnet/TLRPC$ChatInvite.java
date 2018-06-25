package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$ChatInvite extends TLObject {
    public boolean broadcast;
    public boolean channel;
    public TLRPC$Chat chat;
    public int flags;
    public boolean isPublic;
    public boolean megagroup;
    public ArrayList<User> participants = new ArrayList();
    public int participants_count;
    public TLRPC$ChatPhoto photo;
    public String title;

    public static TLRPC$ChatInvite TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChatInvite result = null;
        switch (constructor) {
            case -613092008:
                result = new TLRPC$TL_chatInvite();
                break;
            case 1516793212:
                result = new TLRPC$TL_chatInviteAlready();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChatInvite", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
