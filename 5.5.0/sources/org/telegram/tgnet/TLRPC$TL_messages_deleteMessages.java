package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_deleteMessages extends TLObject {
    public static int constructor = -443640366;
    public int flags;
    public ArrayList<Integer> id = new ArrayList();
    public boolean revoke;

    public static TLRPC$TL_messages_deleteMessages TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_messages_deleteMessages tLRPC$TL_messages_deleteMessages = new TLRPC$TL_messages_deleteMessages();
            tLRPC$TL_messages_deleteMessages.readParams(abstractSerializedData, z);
            return tLRPC$TL_messages_deleteMessages;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_deleteMessages", new Object[]{Integer.valueOf(i)}));
        }
    }

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_affectedMessages.TLdeserialize(abstractSerializedData, i, z);
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.revoke = (this.flags & 1) != 0;
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                this.id.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
                i++;
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.revoke ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(481674261);
        int size = this.id.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
        }
    }
}
