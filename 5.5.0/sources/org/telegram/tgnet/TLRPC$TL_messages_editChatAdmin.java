package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_editChatAdmin extends TLObject {
    public static int constructor = -1444503762;
    public int chat_id;
    public boolean is_admin;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        this.user_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeBool(this.is_admin);
    }
}
