package org.telegram.tgnet;

public class TLRPC$TL_channels_editAdmin extends TLObject {
    public static int constructor = 548962836;
    public TLRPC$TL_channelAdminRights admin_rights;
    public TLRPC$InputChannel channel;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.user_id.serializeToStream(stream);
        this.admin_rights.serializeToStream(stream);
    }
}
