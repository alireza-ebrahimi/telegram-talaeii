package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeSticker_old2 extends TLRPC$TL_documentAttributeSticker {
    public static int constructor = -1723033470;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.alt = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.alt);
    }
}
