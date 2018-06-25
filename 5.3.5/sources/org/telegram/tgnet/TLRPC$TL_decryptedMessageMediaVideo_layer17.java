package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageMediaVideo_layer17 extends TLRPC$TL_decryptedMessageMediaVideo {
    public static int constructor = 1380598109;
    public byte[] thumb;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.thumb = stream.readByteArray(exception);
        this.thumb_w = stream.readInt32(exception);
        this.thumb_h = stream.readInt32(exception);
        this.duration = stream.readInt32(exception);
        this.mime_type = stream.readString(exception);
        this.w = stream.readInt32(exception);
        this.h = stream.readInt32(exception);
        this.size = stream.readInt32(exception);
        this.key = stream.readByteArray(exception);
        this.iv = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.thumb);
        stream.writeInt32(this.thumb_w);
        stream.writeInt32(this.thumb_h);
        stream.writeInt32(this.duration);
        stream.writeString(this.mime_type);
        stream.writeInt32(this.w);
        stream.writeInt32(this.h);
        stream.writeInt32(this.size);
        stream.writeByteArray(this.key);
        stream.writeByteArray(this.iv);
    }
}
