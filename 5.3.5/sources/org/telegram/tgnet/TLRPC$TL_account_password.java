package org.telegram.tgnet;

public class TLRPC$TL_account_password extends TLRPC$account_Password {
    public static int constructor = 2081952796;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.current_salt = stream.readByteArray(exception);
        this.new_salt = stream.readByteArray(exception);
        this.hint = stream.readString(exception);
        this.has_recovery = stream.readBool(exception);
        this.email_unconfirmed_pattern = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.current_salt);
        stream.writeByteArray(this.new_salt);
        stream.writeString(this.hint);
        stream.writeBool(this.has_recovery);
        stream.writeString(this.email_unconfirmed_pattern);
    }
}
