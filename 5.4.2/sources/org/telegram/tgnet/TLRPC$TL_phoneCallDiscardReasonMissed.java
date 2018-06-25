package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_phoneCallDiscardReasonMissed extends PhoneCallDiscardReason {
    public static int constructor = -2048646399;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
