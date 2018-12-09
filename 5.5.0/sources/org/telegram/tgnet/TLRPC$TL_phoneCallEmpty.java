package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCall;

public class TLRPC$TL_phoneCallEmpty extends PhoneCall {
    public static int constructor = 1399245077;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
    }
}
