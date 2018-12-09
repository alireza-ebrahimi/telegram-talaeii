package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.FoundGif;

public class TLRPC$TL_foundGif extends FoundGif {
    public static int constructor = 372165663;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
        this.thumb_url = abstractSerializedData.readString(z);
        this.content_url = abstractSerializedData.readString(z);
        this.content_type = abstractSerializedData.readString(z);
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeString(this.thumb_url);
        abstractSerializedData.writeString(this.content_url);
        abstractSerializedData.writeString(this.content_type);
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
    }
}
