package org.telegram.tgnet;

public class TLRPC$TL_messages_requestEncryption extends TLObject {
    public static int constructor = -162681021;
    public byte[] g_a;
    public int random_id;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$EncryptedChat.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.user_id.serializeToStream(stream);
        stream.writeInt32(this.random_id);
        stream.writeByteArray(this.g_a);
    }
}
