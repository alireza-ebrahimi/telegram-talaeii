package org.telegram.tgnet;

public class TLRPC$TL_auth_resendCode extends TLObject {
    public static int constructor = 1056025023;
    public String phone_code_hash;
    public String phone_number;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_sentCode.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.phone_number);
        stream.writeString(this.phone_code_hash);
    }
}
