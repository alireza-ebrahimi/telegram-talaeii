package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeHasStickers extends DocumentAttribute {
    public static int constructor = -1744710921;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
