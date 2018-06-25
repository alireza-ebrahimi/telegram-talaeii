package org.telegram.tgnet;

public class TLRPC$TL_updates_differenceEmpty extends TLRPC$updates_Difference {
    public static int constructor = 1567990072;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.date = stream.readInt32(exception);
        this.seq = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.date);
        stream.writeInt32(this.seq);
    }
}
