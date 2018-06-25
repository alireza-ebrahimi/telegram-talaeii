package org.telegram.tgnet;

public class TLRPC$TL_messages_getGameHighScores extends TLObject {
    public static int constructor = -400399203;
    public int id;
    public TLRPC$InputPeer peer;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_highScores.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.id);
        this.user_id.serializeToStream(stream);
    }
}
