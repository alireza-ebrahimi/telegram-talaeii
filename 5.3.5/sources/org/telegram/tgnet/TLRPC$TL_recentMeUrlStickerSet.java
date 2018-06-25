package org.telegram.tgnet;

public class TLRPC$TL_recentMeUrlStickerSet extends TLRPC$RecentMeUrl {
    public static int constructor = -1140172836;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.set = TLRPC$StickerSetCovered.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        this.set.serializeToStream(stream);
    }
}
