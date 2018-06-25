package org.telegram.tgnet;

public class TLRPC$TL_messages_sentEncryptedFile extends TLRPC$messages_SentEncryptedMessage {
    public static int constructor = -1802240206;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.date = stream.readInt32(exception);
        this.file = TLRPC$EncryptedFile.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.date);
        this.file.serializeToStream(stream);
    }
}
