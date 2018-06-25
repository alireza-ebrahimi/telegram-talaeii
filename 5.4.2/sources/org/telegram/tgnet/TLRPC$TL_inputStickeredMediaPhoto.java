package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPhoto;
import org.telegram.tgnet.TLRPC.InputStickeredMedia;

public class TLRPC$TL_inputStickeredMediaPhoto extends InputStickeredMedia {
    public static int constructor = 1251549527;
    public InputPhoto id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = InputPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.id.serializeToStream(abstractSerializedData);
    }
}
