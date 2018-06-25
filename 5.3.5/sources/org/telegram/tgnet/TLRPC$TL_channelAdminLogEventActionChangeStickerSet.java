package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionChangeStickerSet extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = -1312568665;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.prev_stickerset = TLRPC$InputStickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.new_stickerset = TLRPC$InputStickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.prev_stickerset.serializeToStream(stream);
        this.new_stickerset.serializeToStream(stream);
    }
}
