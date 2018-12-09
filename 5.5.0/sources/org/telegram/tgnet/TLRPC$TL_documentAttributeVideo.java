package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeVideo extends DocumentAttribute {
    public static int constructor = 250621158;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.round_message = (this.flags & 1) != 0;
        this.duration = abstractSerializedData.readInt32(z);
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.round_message ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.duration);
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
    }
}
