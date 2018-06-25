package org.telegram.tgnet;

public class TLRPC$TL_messages_startBot extends TLObject {
    public static int constructor = -421563528;
    public TLRPC$InputUser bot;
    public TLRPC$InputPeer peer;
    public long random_id;
    public String start_param;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.bot.serializeToStream(stream);
        this.peer.serializeToStream(stream);
        stream.writeInt64(this.random_id);
        stream.writeString(this.start_param);
    }
}
