package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeSticker_old extends TLRPC$TL_documentAttributeSticker {
    public static int constructor = -83208409;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
