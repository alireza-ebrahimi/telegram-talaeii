package org.telegram.tgnet;

public class TLRPC$TL_inputStickerSetID extends TLRPC$InputStickerSet {
    public static int constructor = -1645763991;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
    }
}
