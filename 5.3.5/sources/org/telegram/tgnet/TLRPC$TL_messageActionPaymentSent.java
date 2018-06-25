package org.telegram.tgnet;

public class TLRPC$TL_messageActionPaymentSent extends TLRPC$MessageAction {
    public static int constructor = 1080663248;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.currency = stream.readString(exception);
        this.total_amount = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.currency);
        stream.writeInt64(this.total_amount);
    }
}
