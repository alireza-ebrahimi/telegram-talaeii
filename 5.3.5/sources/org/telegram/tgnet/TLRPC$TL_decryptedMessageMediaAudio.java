package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageMediaAudio extends TLRPC$DecryptedMessageMedia {
    public static int constructor = 1474341323;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.duration = stream.readInt32(exception);
        this.mime_type = stream.readString(exception);
        this.size = stream.readInt32(exception);
        this.key = stream.readByteArray(exception);
        this.iv = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.duration);
        stream.writeString(this.mime_type);
        stream.writeInt32(this.size);
        stream.writeByteArray(this.key);
        stream.writeByteArray(this.iv);
    }
}
