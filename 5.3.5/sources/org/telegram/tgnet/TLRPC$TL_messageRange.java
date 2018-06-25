package org.telegram.tgnet;

public class TLRPC$TL_messageRange extends TLObject {
    public static int constructor = 182649427;
    public int max_id;
    public int min_id;

    public static TLRPC$TL_messageRange TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messageRange result = new TLRPC$TL_messageRange();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messageRange", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.min_id = stream.readInt32(exception);
        this.max_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.min_id);
        stream.writeInt32(this.max_id);
    }
}
