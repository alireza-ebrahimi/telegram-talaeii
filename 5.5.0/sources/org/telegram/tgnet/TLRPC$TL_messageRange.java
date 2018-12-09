package org.telegram.tgnet;

public class TLRPC$TL_messageRange extends TLObject {
    public static int constructor = 182649427;
    public int max_id;
    public int min_id;

    public static TLRPC$TL_messageRange TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_messageRange tLRPC$TL_messageRange = new TLRPC$TL_messageRange();
            tLRPC$TL_messageRange.readParams(abstractSerializedData, z);
            return tLRPC$TL_messageRange;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messageRange", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.min_id = abstractSerializedData.readInt32(z);
        this.max_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.min_id);
        abstractSerializedData.writeInt32(this.max_id);
    }
}
