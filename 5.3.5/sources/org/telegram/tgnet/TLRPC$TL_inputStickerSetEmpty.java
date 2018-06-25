package org.telegram.tgnet;

public class TLRPC$TL_inputStickerSetEmpty extends TLRPC$InputStickerSet {
    public static int constructor = -4838507;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
