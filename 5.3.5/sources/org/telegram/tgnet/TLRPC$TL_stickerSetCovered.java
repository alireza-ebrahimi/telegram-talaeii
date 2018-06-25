package org.telegram.tgnet;

public class TLRPC$TL_stickerSetCovered extends TLRPC$StickerSetCovered {
    public static int constructor = 1678812626;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.set = TLRPC$StickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.cover = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.set.serializeToStream(stream);
        this.cover.serializeToStream(stream);
    }
}
