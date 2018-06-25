package org.telegram.tgnet;

public class TLRPC$TL_updateMessageID extends TLRPC$Update {
    public static int constructor = 1318109142;
    public int id;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.random_id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt64(this.random_id);
    }
}
