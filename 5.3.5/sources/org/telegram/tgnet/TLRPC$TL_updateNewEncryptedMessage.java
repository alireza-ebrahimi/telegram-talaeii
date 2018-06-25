package org.telegram.tgnet;

public class TLRPC$TL_updateNewEncryptedMessage extends TLRPC$Update {
    public static int constructor = 314359194;
    public TLRPC$EncryptedMessage message;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.message = TLRPC$EncryptedMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.qts = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.message.serializeToStream(stream);
        stream.writeInt32(this.qts);
    }
}
