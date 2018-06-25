package org.telegram.tgnet;

public class TLRPC$TL_auth_importAuthorization extends TLObject {
    public static int constructor = -470837741;
    public byte[] bytes;
    public int id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_authorization.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeByteArray(this.bytes);
    }
}
