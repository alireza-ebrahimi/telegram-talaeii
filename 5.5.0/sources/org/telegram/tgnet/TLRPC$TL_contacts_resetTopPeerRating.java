package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_contacts_resetTopPeerRating extends TLObject {
    public static int constructor = 451113900;
    public TLRPC$TopPeerCategory category;
    public InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.category.serializeToStream(abstractSerializedData);
        this.peer.serializeToStream(abstractSerializedData);
    }
}
