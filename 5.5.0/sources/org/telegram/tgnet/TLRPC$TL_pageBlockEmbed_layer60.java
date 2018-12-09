package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockEmbed_layer60 extends TLRPC$TL_pageBlockEmbed {
    public static int constructor = -650782469;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.full_width = (this.flags & 1) != 0;
        if ((this.flags & 8) == 0) {
            z2 = false;
        }
        this.allow_scrolling = z2;
        if ((this.flags & 2) != 0) {
            this.url = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.html = abstractSerializedData.readString(z);
        }
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
        this.caption = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.full_width ? this.flags | 1 : this.flags & -2;
        this.flags = this.allow_scrolling ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.url);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.html);
        }
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
        this.caption.serializeToStream(abstractSerializedData);
    }
}
