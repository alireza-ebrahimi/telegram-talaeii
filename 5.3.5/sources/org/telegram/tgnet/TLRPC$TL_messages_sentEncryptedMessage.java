package org.telegram.tgnet;

public class TLRPC$TL_messages_sentEncryptedMessage extends TLRPC$messages_SentEncryptedMessage {
    public static int constructor = 1443858741;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.date);
    }
}
