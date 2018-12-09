package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChatPhoto;
import org.telegram.tgnet.TLRPC.InputPhoto;

public class TLRPC$TL_inputChatPhoto extends InputChatPhoto {
    public static int constructor = -1991004873;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = InputPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.id.serializeToStream(abstractSerializedData);
    }
}
