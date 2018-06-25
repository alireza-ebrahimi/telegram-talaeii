package org.telegram.tgnet;

public class TLRPC$TL_phoneCallEmpty extends TLRPC$PhoneCall {
    public static int constructor = 1399245077;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
    }
}
