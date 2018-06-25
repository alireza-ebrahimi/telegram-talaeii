package org.telegram.tgnet;

public class TLRPC$TL_messages_getInlineBotResults extends TLObject {
    public static int constructor = 1364105629;
    public TLRPC$InputUser bot;
    public int flags;
    public TLRPC$InputGeoPoint geo_point;
    public String offset;
    public TLRPC$InputPeer peer;
    public String query;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_BotResults.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.bot.serializeToStream(stream);
        this.peer.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            this.geo_point.serializeToStream(stream);
        }
        stream.writeString(this.query);
        stream.writeString(this.offset);
    }
}
