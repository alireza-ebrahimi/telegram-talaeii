package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPaymentCredentials;

public class TLRPC$TL_inputPaymentCredentialsSaved extends InputPaymentCredentials {
    public static int constructor = -1056001329;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readString(z);
        this.tmp_password = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.id);
        abstractSerializedData.writeByteArray(this.tmp_password);
    }
}
