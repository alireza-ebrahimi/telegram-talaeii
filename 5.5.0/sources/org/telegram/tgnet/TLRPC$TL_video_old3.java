package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhotoSize;

public class TLRPC$TL_video_old3 extends TLRPC$TL_video_layer45 {
    public static int constructor = -291550643;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.duration = abstractSerializedData.readInt32(z);
        this.size = abstractSerializedData.readInt32(z);
        this.thumb = PhotoSize.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.dc_id = abstractSerializedData.readInt32(z);
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeInt32(this.duration);
        abstractSerializedData.writeInt32(this.size);
        this.thumb.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.dc_id);
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
    }
}
