package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChatPhoto;

public class TLRPC$TL_messages_editChatPhoto extends TLObject {
    public static int constructor = -900957736;
    public int chat_id;
    public InputChatPhoto photo;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        this.photo.serializeToStream(abstractSerializedData);
    }
}
