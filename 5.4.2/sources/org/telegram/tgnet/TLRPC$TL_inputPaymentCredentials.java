package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPaymentCredentials;

public class TLRPC$TL_inputPaymentCredentials extends InputPaymentCredentials {
    public static int constructor = 873977640;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.save = (this.flags & 1) != 0;
        this.data = TLRPC$TL_dataJSON.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.save ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        this.data.serializeToStream(abstractSerializedData);
    }
}
