package org.telegram.tgnet;

public class TLRPC$TL_updates_differenceEmpty extends TLRPC$updates_Difference {
    public static int constructor = 1567990072;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.date = abstractSerializedData.readInt32(z);
        this.seq = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeInt32(this.seq);
    }
}
