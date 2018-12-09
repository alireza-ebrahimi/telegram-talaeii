package org.telegram.tgnet;

public class TLRPC$TL_updates_differenceTooLong extends TLRPC$updates_Difference {
    public static int constructor = 1258196845;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.pts = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.pts);
    }
}
