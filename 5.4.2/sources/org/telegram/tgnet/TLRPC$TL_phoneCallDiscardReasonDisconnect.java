package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_phoneCallDiscardReasonDisconnect extends PhoneCallDiscardReason {
    public static int constructor = -527056480;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
