package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeSticker_layer55 extends TLRPC$TL_documentAttributeSticker {
    public static int constructor = 978674434;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.alt = stream.readString(exception);
        this.stickerset = TLRPC$InputStickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.alt);
        this.stickerset.serializeToStream(stream);
    }
}
