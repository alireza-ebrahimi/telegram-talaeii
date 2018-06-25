package org.telegram.tgnet;

public class TLRPC$TL_inputStickerSetShortName extends TLRPC$InputStickerSet {
    public static int constructor = -2044933984;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.short_name = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.short_name);
    }
}
