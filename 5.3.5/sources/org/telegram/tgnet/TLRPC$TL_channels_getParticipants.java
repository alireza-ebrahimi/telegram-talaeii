package org.telegram.tgnet;

public class TLRPC$TL_channels_getParticipants extends TLObject {
    public static int constructor = 306054633;
    public TLRPC$InputChannel channel;
    public TLRPC$ChannelParticipantsFilter filter;
    public int hash;
    public int limit;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$channels_ChannelParticipants.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.filter.serializeToStream(stream);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.limit);
        stream.writeInt32(this.hash);
    }
}
