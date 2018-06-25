package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.PageBlock;

public class TLRPC$TL_pageBlockChannel extends PageBlock {
    public static int constructor = -283684427;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.channel = Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.channel.serializeToStream(abstractSerializedData);
    }
}
