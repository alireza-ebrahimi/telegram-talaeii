package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$contacts_Blocked extends TLObject {
    public ArrayList<TLRPC$TL_contactBlocked> blocked = new ArrayList();
    public int count;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$contacts_Blocked TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$contacts_Blocked tLRPC$contacts_Blocked = null;
        switch (i) {
            case -1878523231:
                tLRPC$contacts_Blocked = new TLRPC$TL_contacts_blockedSlice();
                break;
            case 471043349:
                tLRPC$contacts_Blocked = new TLRPC$TL_contacts_blocked();
                break;
        }
        if (tLRPC$contacts_Blocked == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in contacts_Blocked", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$contacts_Blocked != null) {
            tLRPC$contacts_Blocked.readParams(abstractSerializedData, z);
        }
        return tLRPC$contacts_Blocked;
    }
}
