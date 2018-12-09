package org.telegram.tgnet;

public class TLRPC$TL_updateInlineBotCallbackQuery extends TLRPC$Update {
    public static int constructor = -103646630;
    public TLRPC$TL_inputBotInlineMessageID msg_id;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.query_id = abstractSerializedData.readInt64(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.msg_id = TLRPC$TL_inputBotInlineMessageID.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.chat_instance = abstractSerializedData.readInt64(z);
        if ((this.flags & 1) != 0) {
            this.data = abstractSerializedData.readByteArray(z);
        }
        if ((this.flags & 2) != 0) {
            this.game_short_name = abstractSerializedData.readString(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.query_id);
        abstractSerializedData.writeInt32(this.user_id);
        this.msg_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt64(this.chat_instance);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeByteArray(this.data);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.game_short_name);
        }
    }
}
