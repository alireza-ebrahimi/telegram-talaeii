package org.telegram.tgnet;

public class TLRPC$TL_messages_sendInlineBotResult extends TLObject {
    public static int constructor = -1318189314;
    public boolean background;
    public boolean clear_draft;
    public int flags;
    public String id;
    public TLRPC$InputPeer peer;
    public long query_id;
    public long random_id;
    public int reply_to_msg_id;
    public boolean silent;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.silent ? this.flags | 32 : this.flags & -33;
        this.flags = this.background ? this.flags | 64 : this.flags & -65;
        this.flags = this.clear_draft ? this.flags | 128 : this.flags & -129;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.reply_to_msg_id);
        }
        stream.writeInt64(this.random_id);
        stream.writeInt64(this.query_id);
        stream.writeString(this.id);
    }
}
