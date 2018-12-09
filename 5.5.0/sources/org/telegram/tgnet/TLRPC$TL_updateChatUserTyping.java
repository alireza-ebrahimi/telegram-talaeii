package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.SendMessageAction;

public class TLRPC$TL_updateChatUserTyping extends TLRPC$Update {
    public static int constructor = -1704596961;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.action = SendMessageAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeInt32(this.user_id);
        this.action.serializeToStream(abstractSerializedData);
    }
}
