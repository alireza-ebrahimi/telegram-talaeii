package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$contacts_Blocked extends TLObject {
    public ArrayList<TLRPC$TL_contactBlocked> blocked = new ArrayList();
    public int count;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$contacts_Blocked TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$contacts_Blocked result = null;
        switch (constructor) {
            case -1878523231:
                result = new TLRPC$TL_contacts_blockedSlice();
                break;
            case 471043349:
                result = new TLRPC$TL_contacts_blocked();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in contacts_Blocked", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
