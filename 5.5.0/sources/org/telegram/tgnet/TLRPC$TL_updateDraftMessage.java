package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_updateDraftMessage extends TLRPC$Update {
    public static int constructor = -299124375;
    public Peer peer;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.peer = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.draft = DraftMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.draft.serializeToStream(abstractSerializedData);
    }
}
