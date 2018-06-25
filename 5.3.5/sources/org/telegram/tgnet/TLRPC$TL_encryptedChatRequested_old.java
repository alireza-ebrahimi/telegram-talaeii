package org.telegram.tgnet;

public class TLRPC$TL_encryptedChatRequested_old extends TLRPC$TL_encryptedChatRequested {
    public static int constructor = -39213129;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
        this.participant_id = stream.readInt32(exception);
        this.g_a = stream.readByteArray(exception);
        this.nonce = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.date);
        stream.writeInt32(this.admin_id);
        stream.writeInt32(this.participant_id);
        stream.writeByteArray(this.g_a);
        stream.writeByteArray(this.nonce);
    }
}
