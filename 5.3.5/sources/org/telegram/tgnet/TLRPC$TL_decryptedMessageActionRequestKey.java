package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionRequestKey extends TLRPC$DecryptedMessageAction {
    public static int constructor = -204906213;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.exchange_id = stream.readInt64(exception);
        this.g_a = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.exchange_id);
        stream.writeByteArray(this.g_a);
    }
}
