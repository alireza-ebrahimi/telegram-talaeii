package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messages_getWebPagePreview extends TLObject {
    public static int constructor = 623001124;
    public String message;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return MessageMedia.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.message);
    }
}
