package org.telegram.tgnet;

public class TLRPC$TL_messages_sendScreenshotNotification extends TLObject {
    public static int constructor = -914493408;
    public TLRPC$InputPeer peer;
    public long random_id;
    public int reply_to_msg_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.reply_to_msg_id);
        stream.writeInt64(this.random_id);
    }
}
