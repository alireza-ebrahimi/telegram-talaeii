package org.telegram.tgnet;

public class TLRPC$TL_auth_signUp extends TLObject {
    public static int constructor = 453408308;
    public String first_name;
    public String last_name;
    public String phone_code;
    public String phone_code_hash;
    public String phone_number;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_authorization.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.phone_number);
        stream.writeString(this.phone_code_hash);
        stream.writeString(this.phone_code);
        stream.writeString(this.first_name);
        stream.writeString(this.last_name);
    }
}
