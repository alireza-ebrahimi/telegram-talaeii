package org.telegram.tgnet;

public class TLRPC$TL_messageMediaUnsupported_old extends TLRPC$TL_messageMediaUnsupported {
    public static int constructor = 694364726;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.bytes = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.bytes);
    }
}
