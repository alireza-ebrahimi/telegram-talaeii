package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputSingleMedia extends TLObject {
    public static int constructor = 1588230153;
    public InputMedia media;
    public long random_id;

    public static TLRPC$TL_inputSingleMedia TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputSingleMedia tLRPC$TL_inputSingleMedia = new TLRPC$TL_inputSingleMedia();
            tLRPC$TL_inputSingleMedia.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputSingleMedia;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputSingleMedia", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.media = InputMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.random_id = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.media.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt64(this.random_id);
    }
}
