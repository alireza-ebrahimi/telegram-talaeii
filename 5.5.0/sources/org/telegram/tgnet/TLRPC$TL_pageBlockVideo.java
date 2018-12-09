package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockVideo extends PageBlock {
    public static int constructor = -640214938;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.autoplay = (this.flags & 1) != 0;
        if ((this.flags & 2) == 0) {
            z2 = false;
        }
        this.loop = z2;
        this.video_id = abstractSerializedData.readInt64(z);
        this.caption = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.autoplay ? this.flags | 1 : this.flags & -2;
        this.flags = this.loop ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.video_id);
        this.caption.serializeToStream(abstractSerializedData);
    }
}
