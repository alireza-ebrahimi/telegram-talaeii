package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageEntity;

public class TLRPC$TL_messageEntityTextUrl extends MessageEntity {
    public static int constructor = 1990644519;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.offset = abstractSerializedData.readInt32(z);
        this.length = abstractSerializedData.readInt32(z);
        this.url = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.length);
        abstractSerializedData.writeString(this.url);
    }
}
