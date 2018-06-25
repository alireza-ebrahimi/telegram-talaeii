package org.telegram.tgnet;

public class TLRPC$TL_account_tmpPassword extends TLObject {
    public static int constructor = -614138572;
    public byte[] tmp_password;
    public int valid_until;

    public static TLRPC$TL_account_tmpPassword TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_account_tmpPassword result = new TLRPC$TL_account_tmpPassword();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_account_tmpPassword", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.tmp_password = stream.readByteArray(exception);
        this.valid_until = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.tmp_password);
        stream.writeInt32(this.valid_until);
    }
}
