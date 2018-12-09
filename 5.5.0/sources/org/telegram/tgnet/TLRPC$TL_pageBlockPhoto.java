package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockPhoto extends PageBlock {
    public static int constructor = -372860542;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo_id = abstractSerializedData.readInt64(z);
        this.caption = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.photo_id);
        this.caption.serializeToStream(abstractSerializedData);
    }
}
