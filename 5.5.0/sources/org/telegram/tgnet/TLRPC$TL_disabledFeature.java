package org.telegram.tgnet;

public class TLRPC$TL_disabledFeature extends TLObject {
    public static int constructor = -1369215196;
    public String description;
    public String feature;

    public static TLRPC$TL_disabledFeature TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_disabledFeature tLRPC$TL_disabledFeature = new TLRPC$TL_disabledFeature();
            tLRPC$TL_disabledFeature.readParams(abstractSerializedData, z);
            return tLRPC$TL_disabledFeature;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_disabledFeature", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.feature = abstractSerializedData.readString(z);
        this.description = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.feature);
        abstractSerializedData.writeString(this.description);
    }
}
