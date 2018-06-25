package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoChatMessage;

public class TLRPC$TL_geoChatMessageEmpty extends GeoChatMessage {
    public static int constructor = 1613830811;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeInt32(this.id);
    }
}
