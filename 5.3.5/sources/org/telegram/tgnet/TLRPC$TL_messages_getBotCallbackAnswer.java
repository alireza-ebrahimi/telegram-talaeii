package org.telegram.tgnet;

public class TLRPC$TL_messages_getBotCallbackAnswer extends TLObject {
    public static int constructor = -2130010132;
    public byte[] data;
    public int flags;
    public boolean game;
    public int msg_id;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_botCallbackAnswer.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.game ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.msg_id);
        if ((this.flags & 1) != 0) {
            stream.writeByteArray(this.data);
        }
    }
}
