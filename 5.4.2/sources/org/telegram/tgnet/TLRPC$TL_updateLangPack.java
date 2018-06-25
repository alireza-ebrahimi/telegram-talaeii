package org.telegram.tgnet;

public class TLRPC$TL_updateLangPack extends TLRPC$Update {
    public static int constructor = 1442983757;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.difference = TLRPC$TL_langPackDifference.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.difference.serializeToStream(abstractSerializedData);
    }
}
