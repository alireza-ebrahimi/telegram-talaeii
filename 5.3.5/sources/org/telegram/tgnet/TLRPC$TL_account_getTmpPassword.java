package org.telegram.tgnet;

public class TLRPC$TL_account_getTmpPassword extends TLObject {
    public static int constructor = 1250046590;
    public byte[] password_hash;
    public int period;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_account_tmpPassword.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.password_hash);
        stream.writeInt32(this.period);
    }
}
