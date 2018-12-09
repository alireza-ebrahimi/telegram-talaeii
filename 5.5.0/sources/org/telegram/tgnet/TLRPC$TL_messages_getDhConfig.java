package org.telegram.tgnet;

public class TLRPC$TL_messages_getDhConfig extends TLObject {
    public static int constructor = 651135312;
    public int random_length;
    public int version;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_DhConfig.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.version);
        abstractSerializedData.writeInt32(this.random_length);
    }
}
