package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_updatePinnedDialogs extends TLRPC$Update {
    public static int constructor = -657787251;
    public ArrayList<Peer> order = new ArrayList();

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    Peer TLdeserialize = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.order.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.order.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((Peer) this.order.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }
}
