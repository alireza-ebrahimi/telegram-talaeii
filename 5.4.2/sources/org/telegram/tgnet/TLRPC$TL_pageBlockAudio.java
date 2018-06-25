package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockAudio extends PageBlock {
    public static int constructor = 834148991;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.audio_id = abstractSerializedData.readInt64(z);
        this.caption = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.audio_id);
        this.caption.serializeToStream(abstractSerializedData);
    }
}
