package org.telegram.tgnet;

public class TLRPC$TL_encryptedMessageService extends TLRPC$EncryptedMessage {
    public static int constructor = 594758406;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.random_id = stream.readInt64(exception);
        this.chat_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.bytes = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.random_id);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.date);
        stream.writeByteArray(this.bytes);
    }
}
