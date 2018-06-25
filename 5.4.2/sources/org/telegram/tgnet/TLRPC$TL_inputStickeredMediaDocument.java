package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputDocument;
import org.telegram.tgnet.TLRPC.InputStickeredMedia;

public class TLRPC$TL_inputStickeredMediaDocument extends InputStickeredMedia {
    public static int constructor = 70813275;
    public InputDocument id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = InputDocument.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.id.serializeToStream(abstractSerializedData);
    }
}
