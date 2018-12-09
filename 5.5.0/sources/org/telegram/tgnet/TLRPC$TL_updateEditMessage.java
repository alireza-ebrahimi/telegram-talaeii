package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Message;

public class TLRPC$TL_updateEditMessage extends TLRPC$Update {
    public static int constructor = -469536605;
    public Message message;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.message = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.message.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.pts_count);
    }
}
