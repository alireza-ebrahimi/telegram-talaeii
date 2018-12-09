package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Bool;

public class TLRPC$TL_messages_reorderStickerSets extends TLObject {
    public static int constructor = 2016638777;
    public int flags;
    public boolean masks;
    public ArrayList<Long> order = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.masks ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(481674261);
        int size = this.order.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt64(((Long) this.order.get(i)).longValue());
        }
    }
}
