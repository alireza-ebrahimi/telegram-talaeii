package org.telegram.tgnet;

public class TLRPC$TL_inputPaymentCredentialsSaved extends TLRPC$InputPaymentCredentials {
    public static int constructor = -1056001329;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readString(exception);
        this.tmp_password = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.id);
        stream.writeByteArray(this.tmp_password);
    }
}
