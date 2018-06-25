package org.telegram.tgnet;

public class TLRPC$TL_channels_editBanned extends TLObject {
    public static int constructor = -1076292147;
    public TLRPC$TL_channelBannedRights banned_rights;
    public TLRPC$InputChannel channel;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.user_id.serializeToStream(stream);
        this.banned_rights.serializeToStream(stream);
    }
}
