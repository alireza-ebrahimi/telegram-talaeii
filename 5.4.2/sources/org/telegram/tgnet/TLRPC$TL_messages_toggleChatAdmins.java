package org.telegram.tgnet;

public class TLRPC$TL_messages_toggleChatAdmins extends TLObject {
    public static int constructor = -326379039;
    public int chat_id;
    public boolean enabled;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeBool(this.enabled);
    }
}
