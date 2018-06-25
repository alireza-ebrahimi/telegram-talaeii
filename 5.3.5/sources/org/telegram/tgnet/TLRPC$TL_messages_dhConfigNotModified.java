package org.telegram.tgnet;

public class TLRPC$TL_messages_dhConfigNotModified extends TLRPC$messages_DhConfig {
    public static int constructor = -1058912715;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.random = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.random);
    }
}
