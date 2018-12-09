package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_getMessagesViews extends TLObject {
    public static int constructor = -993483427;
    public ArrayList<Integer> id = new ArrayList();
    public boolean increment;
    public InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLObject tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            tLRPC$Vector.objects.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
        }
        return tLRPC$Vector;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.id.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
        }
        abstractSerializedData.writeBool(this.increment);
    }
}
