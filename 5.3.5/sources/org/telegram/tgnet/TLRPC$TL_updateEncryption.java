package org.telegram.tgnet;

public class TLRPC$TL_updateEncryption extends TLRPC$Update {
    public static int constructor = -1264392051;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat = TLRPC$EncryptedChat.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.chat.serializeToStream(stream);
        stream.writeInt32(this.date);
    }
}
