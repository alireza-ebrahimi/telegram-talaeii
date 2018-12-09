package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_updateReadHistoryInbox extends TLRPC$Update {
    public static int constructor = -1721631396;
    public Peer peer;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.peer = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.max_id = abstractSerializedData.readInt32(z);
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.max_id);
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.pts_count);
    }
}
