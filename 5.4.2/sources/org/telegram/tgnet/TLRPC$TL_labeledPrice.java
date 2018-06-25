package org.telegram.tgnet;

public class TLRPC$TL_labeledPrice extends TLObject {
    public static int constructor = -886477832;
    public long amount;
    public String label;

    public static TLRPC$TL_labeledPrice TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_labeledPrice tLRPC$TL_labeledPrice = new TLRPC$TL_labeledPrice();
            tLRPC$TL_labeledPrice.readParams(abstractSerializedData, z);
            return tLRPC$TL_labeledPrice;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_labeledPrice", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.label = abstractSerializedData.readString(z);
        this.amount = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.label);
        abstractSerializedData.writeInt64(this.amount);
    }
}
