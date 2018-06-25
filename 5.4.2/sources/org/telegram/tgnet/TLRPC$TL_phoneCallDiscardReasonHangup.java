package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_phoneCallDiscardReasonHangup extends PhoneCallDiscardReason {
    public static int constructor = 1471006352;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
