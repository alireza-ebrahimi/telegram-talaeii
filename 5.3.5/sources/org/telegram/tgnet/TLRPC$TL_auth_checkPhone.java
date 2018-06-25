package org.telegram.tgnet;

public class TLRPC$TL_auth_checkPhone extends TLObject {
    public static int constructor = 1877286395;
    public String phone_number;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_checkedPhone.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.phone_number);
    }
}
