package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_updateBotCallbackQuery extends TLRPC$Update {
    public static int constructor = -415938591;
    public int msg_id;
    public Peer peer;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.query_id = abstractSerializedData.readInt64(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.peer = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.msg_id = abstractSerializedData.readInt32(z);
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
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.msg_id);
        abstractSerializedData.writeInt64(this.chat_instance);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeByteArray(this.data);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.game_short_name);
        }
    }
}
