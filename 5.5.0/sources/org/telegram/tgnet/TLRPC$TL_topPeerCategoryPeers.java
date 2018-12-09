package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_topPeerCategoryPeers extends TLObject {
    public static int constructor = -75283823;
    public TLRPC$TopPeerCategory category;
    public int count;
    public ArrayList<TLRPC$TL_topPeer> peers = new ArrayList();

    public static TLRPC$TL_topPeerCategoryPeers TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_topPeerCategoryPeers tLRPC$TL_topPeerCategoryPeers = new TLRPC$TL_topPeerCategoryPeers();
            tLRPC$TL_topPeerCategoryPeers.readParams(abstractSerializedData, z);
            return tLRPC$TL_topPeerCategoryPeers;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_topPeerCategoryPeers", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.category = TLRPC$TopPeerCategory.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.count = abstractSerializedData.readInt32(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                TLRPC$TL_topPeer TLdeserialize = TLRPC$TL_topPeer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.peers.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.category.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.count);
        abstractSerializedData.writeInt32(481674261);
        int size = this.peers.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TLRPC$TL_topPeer) this.peers.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
