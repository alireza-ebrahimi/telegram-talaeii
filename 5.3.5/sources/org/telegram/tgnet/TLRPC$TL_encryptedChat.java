package org.telegram.tgnet;

public class TLRPC$TL_encryptedChat extends TLRPC$EncryptedChat {
    public static int constructor = -94974410;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
        this.participant_id = stream.readInt32(exception);
        this.g_a_or_b = stream.readByteArray(exception);
        this.key_fingerprint = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.date);
        stream.writeInt32(this.admin_id);
        stream.writeInt32(this.participant_id);
        stream.writeByteArray(this.g_a_or_b);
        stream.writeInt64(this.key_fingerprint);
    }
}
