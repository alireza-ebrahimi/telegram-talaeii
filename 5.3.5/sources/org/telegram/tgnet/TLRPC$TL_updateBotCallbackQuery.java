package org.telegram.tgnet;

public class TLRPC$TL_updateBotCallbackQuery extends TLRPC$Update {
    public static int constructor = -415938591;
    public int msg_id;
    public TLRPC$Peer peer;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.query_id = stream.readInt64(exception);
        this.user_id = stream.readInt32(exception);
        this.peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.msg_id = stream.readInt32(exception);
        this.chat_instance = stream.readInt64(exception);
        if ((this.flags & 1) != 0) {
            this.data = stream.readByteArray(exception);
        }
        if ((this.flags & 2) != 0) {
            this.game_short_name = stream.readString(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.query_id);
        stream.writeInt32(this.user_id);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.msg_id);
        stream.writeInt64(this.chat_instance);
        if ((this.flags & 1) != 0) {
            stream.writeByteArray(this.data);
        }
        if ((this.flags & 2) != 0) {
            stream.writeString(this.game_short_name);
        }
    }
}
