package org.telegram.tgnet;

public class TLRPC$TL_account_noPassword extends TLRPC$account_Password {
    public static int constructor = -1764049896;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.new_salt = stream.readByteArray(exception);
        this.email_unconfirmed_pattern = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.new_salt);
        stream.writeString(this.email_unconfirmed_pattern);
    }
}
