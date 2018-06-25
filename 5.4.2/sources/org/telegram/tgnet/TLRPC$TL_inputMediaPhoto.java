package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputMedia;
import org.telegram.tgnet.TLRPC.InputPhoto;

public class TLRPC$TL_inputMediaPhoto extends InputMedia {
    public static int constructor = -2114308294;
    public InputPhoto id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.id = InputPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.caption = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.ttl_seconds = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.ttl_seconds);
        }
    }
}
