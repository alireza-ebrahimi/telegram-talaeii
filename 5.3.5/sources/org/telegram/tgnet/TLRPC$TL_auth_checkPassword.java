package org.telegram.tgnet;

public class TLRPC$TL_auth_checkPassword extends TLObject {
    public static int constructor = 174260510;
    public byte[] password_hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_authorization.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.password_hash);
    }
}
