package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageMediaDocument_layer8 extends TLRPC$TL_decryptedMessageMediaDocument {
    public static int constructor = -1332395189;
    public byte[] thumb;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.thumb = stream.readByteArray(exception);
        this.thumb_w = stream.readInt32(exception);
        this.thumb_h = stream.readInt32(exception);
        this.file_name = stream.readString(exception);
        this.mime_type = stream.readString(exception);
        this.size = stream.readInt32(exception);
        this.key = stream.readByteArray(exception);
        this.iv = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.thumb);
        stream.writeInt32(this.thumb_w);
        stream.writeInt32(this.thumb_h);
        stream.writeString(this.file_name);
        stream.writeString(this.mime_type);
        stream.writeInt32(this.size);
        stream.writeByteArray(this.key);
        stream.writeByteArray(this.iv);
    }
}
