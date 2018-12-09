package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPaymentCredentials;

public class TLRPC$TL_inputPaymentCredentialsAndroidPay extends InputPaymentCredentials {
    public static int constructor = 2035705766;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.payment_token = TLRPC$TL_dataJSON.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.payment_token.serializeToStream(abstractSerializedData);
    }
}
