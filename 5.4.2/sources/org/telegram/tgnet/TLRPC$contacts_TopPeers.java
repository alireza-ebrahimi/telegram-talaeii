package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$contacts_TopPeers extends TLObject {
    public ArrayList<TLRPC$TL_topPeerCategoryPeers> categories = new ArrayList();
    public ArrayList<Chat> chats = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$contacts_TopPeers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$contacts_TopPeers tLRPC$contacts_TopPeers = null;
        switch (i) {
            case -567906571:
                tLRPC$contacts_TopPeers = new TLRPC$TL_contacts_topPeersNotModified();
                break;
            case 1891070632:
                tLRPC$contacts_TopPeers = new TLRPC$TL_contacts_topPeers();
                break;
        }
        if (tLRPC$contacts_TopPeers == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in contacts_TopPeers", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$contacts_TopPeers != null) {
            tLRPC$contacts_TopPeers.readParams(abstractSerializedData, z);
        }
        return tLRPC$contacts_TopPeers;
    }
}
