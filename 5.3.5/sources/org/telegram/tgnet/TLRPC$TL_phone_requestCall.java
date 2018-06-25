package org.telegram.tgnet;

public class TLRPC$TL_phone_requestCall extends TLObject {
    public static int constructor = 1536537556;
    public byte[] g_a_hash;
    public TLRPC$TL_phoneCallProtocol protocol;
    public int random_id;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_phone_phoneCall.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.user_id.serializeToStream(stream);
        stream.writeInt32(this.random_id);
        stream.writeByteArray(this.g_a_hash);
        this.protocol.serializeToStream(stream);
    }
}
