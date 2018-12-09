package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_reorderPinnedDialogs extends TLObject {
    public static int constructor = -1784678844;
    public int flags;
    public boolean force;
    public ArrayList<InputPeer> order = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.force ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(481674261);
        int size = this.order.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((InputPeer) this.order.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
