package org.telegram.tgnet;

public class TLRPC$TL_nearestDc extends TLObject {
    public static int constructor = -1910892683;
    public String country;
    public int nearest_dc;
    public int this_dc;

    public static TLRPC$TL_nearestDc TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_nearestDc tLRPC$TL_nearestDc = new TLRPC$TL_nearestDc();
            tLRPC$TL_nearestDc.readParams(abstractSerializedData, z);
            return tLRPC$TL_nearestDc;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_nearestDc", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.country = abstractSerializedData.readString(z);
        this.this_dc = abstractSerializedData.readInt32(z);
        this.nearest_dc = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.country);
        abstractSerializedData.writeInt32(this.this_dc);
        abstractSerializedData.writeInt32(this.nearest_dc);
    }
}
