package org.telegram.tgnet;

public class TLRPC$TL_account_confirmPhone extends TLObject {
    public static int constructor = 1596029123;
    public String phone_code;
    public String phone_code_hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.phone_code_hash);
        stream.writeString(this.phone_code);
    }
}
