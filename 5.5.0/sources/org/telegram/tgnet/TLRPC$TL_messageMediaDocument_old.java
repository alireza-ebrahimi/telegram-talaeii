package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;

public class TLRPC$TL_messageMediaDocument_old extends TLRPC$TL_messageMediaDocument {
    public static int constructor = 802824708;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.document = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.document.serializeToStream(abstractSerializedData);
    }
}
