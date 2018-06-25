package org.telegram.tgnet;

public class TLRPC$TL_updateChannel extends TLRPC$Update {
    public static int constructor = -1227598250;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.channel_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.channel_id);
    }
}
