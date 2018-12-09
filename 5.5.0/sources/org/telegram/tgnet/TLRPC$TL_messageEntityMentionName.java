package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageEntity;

public class TLRPC$TL_messageEntityMentionName extends MessageEntity {
    public static int constructor = 892193368;
    public int user_id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.offset = abstractSerializedData.readInt32(z);
        this.length = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.length);
        abstractSerializedData.writeInt32(this.user_id);
    }
}
