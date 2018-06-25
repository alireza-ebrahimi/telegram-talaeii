package org.telegram.tgnet;

public class TLRPC$TL_phoneCallDiscardReasonAllowGroupCall extends TLRPC$PhoneCallDiscardReason {
    public static int constructor = -1344096199;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.encrypted_key = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.encrypted_key);
    }
}
