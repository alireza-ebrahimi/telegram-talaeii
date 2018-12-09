package org.telegram.tgnet;

public class TLRPC$TL_messages_messageEditData extends TLObject {
    public static int constructor = 649453030;
    public boolean caption;
    public int flags;

    public static TLRPC$TL_messages_messageEditData TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_messages_messageEditData tLRPC$TL_messages_messageEditData = new TLRPC$TL_messages_messageEditData();
            tLRPC$TL_messages_messageEditData.readParams(abstractSerializedData, z);
            return tLRPC$TL_messages_messageEditData;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_messageEditData", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.caption = (this.flags & 1) != 0;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.caption ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
    }
}
