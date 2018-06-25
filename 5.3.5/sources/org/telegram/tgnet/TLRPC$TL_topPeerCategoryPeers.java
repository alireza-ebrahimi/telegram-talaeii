package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_topPeerCategoryPeers extends TLObject {
    public static int constructor = -75283823;
    public TLRPC$TopPeerCategory category;
    public int count;
    public ArrayList<TLRPC$TL_topPeer> peers = new ArrayList();

    public static TLRPC$TL_topPeerCategoryPeers TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_topPeerCategoryPeers result = new TLRPC$TL_topPeerCategoryPeers();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_topPeerCategoryPeers", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.category = TLRPC$TopPeerCategory.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.count = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_topPeer object = TLRPC$TL_topPeer.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.peers.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.category.serializeToStream(stream);
        stream.writeInt32(this.count);
        stream.writeInt32(481674261);
        int count = this.peers.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_topPeer) this.peers.get(a)).serializeToStream(stream);
        }
    }
}
