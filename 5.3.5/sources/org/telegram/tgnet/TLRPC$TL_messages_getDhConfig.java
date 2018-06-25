package org.telegram.tgnet;

public class TLRPC$TL_messages_getDhConfig extends TLObject {
    public static int constructor = 651135312;
    public int random_length;
    public int version;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_DhConfig.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.version);
        stream.writeInt32(this.random_length);
    }
}
