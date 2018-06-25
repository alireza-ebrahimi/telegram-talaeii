package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaPhotoExternal extends InputMedia {
    public static int constructor = 153267905;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.url = abstractSerializedData.readString(z);
        this.caption = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.ttl_seconds = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.ttl_seconds);
        }
    }
}
