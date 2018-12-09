package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_phoneCallDiscardReasonAllowGroupCall extends PhoneCallDiscardReason {
    public static int constructor = -1344096199;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.encrypted_key = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeByteArray(this.encrypted_key);
    }
}
