package org.telegram.tgnet;

public class TLRPC$TL_messages_acceptEncryption extends TLObject {
    public static int constructor = 1035731989;
    public byte[] g_b;
    public long key_fingerprint;
    public TLRPC$TL_inputEncryptedChat peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$EncryptedChat.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeByteArray(this.g_b);
        stream.writeInt64(this.key_fingerprint);
    }
}
