package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionAcceptKey extends TLRPC$DecryptedMessageAction {
    public static int constructor = 1877046107;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.exchange_id = stream.readInt64(exception);
        this.g_b = stream.readByteArray(exception);
        this.key_fingerprint = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.exchange_id);
        stream.writeByteArray(this.g_b);
        stream.writeInt64(this.key_fingerprint);
    }
}
