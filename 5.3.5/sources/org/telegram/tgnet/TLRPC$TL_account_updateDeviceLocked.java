package org.telegram.tgnet;

public class TLRPC$TL_account_updateDeviceLocked extends TLObject {
    public static int constructor = 954152242;
    public int period;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.period);
    }
}
