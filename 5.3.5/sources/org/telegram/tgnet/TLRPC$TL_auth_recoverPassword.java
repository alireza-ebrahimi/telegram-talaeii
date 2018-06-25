package org.telegram.tgnet;

public class TLRPC$TL_auth_recoverPassword extends TLObject {
    public static int constructor = 1319464594;
    public String code;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_authorization.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.code);
    }
}
