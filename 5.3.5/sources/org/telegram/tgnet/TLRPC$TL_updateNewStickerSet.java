package org.telegram.tgnet;

public class TLRPC$TL_updateNewStickerSet extends TLRPC$Update {
    public static int constructor = 1753886890;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.stickerset = TLRPC$TL_messages_stickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.stickerset.serializeToStream(stream);
    }
}
