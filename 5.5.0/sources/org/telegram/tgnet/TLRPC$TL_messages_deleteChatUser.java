package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_deleteChatUser extends TLObject {
    public static int constructor = -530505962;
    public int chat_id;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        this.user_id.serializeToStream(abstractSerializedData);
    }
}
