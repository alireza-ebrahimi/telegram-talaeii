package org.telegram.tgnet;

public class TLRPC$TL_channels_deleteUserHistory extends TLObject {
    public static int constructor = -787622117;
    public TLRPC$InputChannel channel;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_affectedHistory.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.user_id.serializeToStream(stream);
    }
}
