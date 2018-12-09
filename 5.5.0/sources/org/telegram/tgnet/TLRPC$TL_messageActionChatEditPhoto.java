package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;
import org.telegram.tgnet.TLRPC.Photo;

public class TLRPC$TL_messageActionChatEditPhoto extends MessageAction {
    public static int constructor = 2144015272;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.photo.serializeToStream(abstractSerializedData);
    }
}
