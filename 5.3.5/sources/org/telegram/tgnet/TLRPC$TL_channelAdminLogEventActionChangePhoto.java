package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionChangePhoto extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = -1204857405;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.prev_photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.new_photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.prev_photo.serializeToStream(stream);
        this.new_photo.serializeToStream(stream);
    }
}
