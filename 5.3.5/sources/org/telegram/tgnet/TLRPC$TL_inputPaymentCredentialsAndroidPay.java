package org.telegram.tgnet;

public class TLRPC$TL_inputPaymentCredentialsAndroidPay extends TLRPC$InputPaymentCredentials {
    public static int constructor = 2035705766;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.payment_token = TLRPC$TL_dataJSON.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.payment_token.serializeToStream(stream);
    }
}
