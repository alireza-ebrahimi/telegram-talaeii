package org.telegram.tgnet;

public class TLRPC$TL_messages_dhConfig extends TLRPC$messages_DhConfig {
    public static int constructor = 740433629;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.g = stream.readInt32(exception);
        this.p = stream.readByteArray(exception);
        this.version = stream.readInt32(exception);
        this.random = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.g);
        stream.writeByteArray(this.p);
        stream.writeInt32(this.version);
        stream.writeByteArray(this.random);
    }
}
