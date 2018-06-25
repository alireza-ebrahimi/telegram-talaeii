package org.telegram.tgnet;

public class TLRPC$TL_chatLocated extends TLObject {
    public static int constructor = 909233996;
    public int chat_id;
    public int distance;

    public static TLRPC$TL_chatLocated TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_chatLocated result = new TLRPC$TL_chatLocated();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_chatLocated", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.distance = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.distance);
    }
}
