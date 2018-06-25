package org.telegram.tgnet;

public class TLRPC$TL_auth_sentCodeTypeFlashCall extends TLRPC$auth_SentCodeType {
    public static int constructor = -1425815847;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.pattern = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.pattern);
    }
}
