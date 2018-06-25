package org.telegram.tgnet;

public class TLRPC$TL_phoneCallDiscardReasonHangup extends TLRPC$PhoneCallDiscardReason {
    public static int constructor = 1471006352;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
