package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessage_layer8 extends TLRPC$TL_decryptedMessage {
    public static int constructor = 528568095;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.random_id = stream.readInt64(exception);
        this.random_bytes = stream.readByteArray(exception);
        this.message = stream.readString(exception);
        this.media = TLRPC$DecryptedMessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.random_id);
        stream.writeByteArray(this.random_bytes);
        stream.writeString(this.message);
        this.media.serializeToStream(stream);
    }
}
