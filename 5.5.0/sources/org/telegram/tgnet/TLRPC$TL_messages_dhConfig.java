package org.telegram.tgnet;

public class TLRPC$TL_messages_dhConfig extends TLRPC$messages_DhConfig {
    public static int constructor = 740433629;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.g = abstractSerializedData.readInt32(z);
        this.p = abstractSerializedData.readByteArray(z);
        this.version = abstractSerializedData.readInt32(z);
        this.random = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.g);
        abstractSerializedData.writeByteArray(this.p);
        abstractSerializedData.writeInt32(this.version);
        abstractSerializedData.writeByteArray(this.random);
    }
}
