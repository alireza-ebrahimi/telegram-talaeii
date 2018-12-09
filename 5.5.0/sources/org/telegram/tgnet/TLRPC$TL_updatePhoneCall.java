package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCall;

public class TLRPC$TL_updatePhoneCall extends TLRPC$Update {
    public static int constructor = -1425052898;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.phone_call = PhoneCall.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.phone_call.serializeToStream(abstractSerializedData);
    }
}
