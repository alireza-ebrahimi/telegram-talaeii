package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.MessageEntity;

public class TLRPC$TL_inputMessageEntityMentionName extends MessageEntity {
    public static int constructor = 546203849;
    public InputUser user_id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.offset = abstractSerializedData.readInt32(z);
        this.length = abstractSerializedData.readInt32(z);
        this.user_id = InputUser.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.length);
        this.user_id.serializeToStream(abstractSerializedData);
    }
}
