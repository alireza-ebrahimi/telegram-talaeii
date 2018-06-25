package org.telegram.tgnet;

public class TLRPC$TL_channels_getParticipant extends TLObject {
    public static int constructor = 1416484774;
    public TLRPC$InputChannel channel;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_channels_channelParticipant.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.user_id.serializeToStream(stream);
    }
}
