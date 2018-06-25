package org.telegram.tgnet;

public class TLRPC$TL_updates_getChannelDifference extends TLObject {
    public static int constructor = 51854712;
    public TLRPC$InputChannel channel;
    public TLRPC$ChannelMessagesFilter filter;
    public int flags;
    public boolean force;
    public int limit;
    public int pts;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$updates_ChannelDifference.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.force ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.channel.serializeToStream(stream);
        this.filter.serializeToStream(stream);
        stream.writeInt32(this.pts);
        stream.writeInt32(this.limit);
    }
}
