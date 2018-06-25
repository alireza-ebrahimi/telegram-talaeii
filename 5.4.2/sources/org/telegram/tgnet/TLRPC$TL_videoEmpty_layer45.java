package org.telegram.tgnet;

public class TLRPC$TL_videoEmpty_layer45 extends TLRPC$Video {
    public static int constructor = -1056548696;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
    }
}
