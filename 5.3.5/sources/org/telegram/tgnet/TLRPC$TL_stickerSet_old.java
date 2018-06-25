package org.telegram.tgnet;

public class TLRPC$TL_stickerSet_old extends TLRPC$TL_stickerSet {
    public static int constructor = -1482409193;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.title = stream.readString(exception);
        this.short_name = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.title);
        stream.writeString(this.short_name);
    }
}
