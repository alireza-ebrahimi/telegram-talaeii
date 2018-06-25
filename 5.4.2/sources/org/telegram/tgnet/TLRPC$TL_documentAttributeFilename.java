package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeFilename extends DocumentAttribute {
    public static int constructor = 358154344;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.file_name = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.file_name);
    }
}
