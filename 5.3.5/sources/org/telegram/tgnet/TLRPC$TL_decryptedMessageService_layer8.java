package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageService_layer8 extends TLRPC$TL_decryptedMessageService {
    public static int constructor = -1438109059;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.random_id = stream.readInt64(exception);
        this.random_bytes = stream.readByteArray(exception);
        this.action = TLRPC$DecryptedMessageAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.random_id);
        stream.writeByteArray(this.random_bytes);
        this.action.serializeToStream(stream);
    }
}
