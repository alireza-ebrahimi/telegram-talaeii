package org.telegram.tgnet;

public class TLRPC$TL_auth_sentCodeTypeApp extends TLRPC$auth_SentCodeType {
    public static int constructor = 1035688326;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.length = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.length);
    }
}
