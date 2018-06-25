package org.telegram.tgnet;

public class TLRPC$TL_groupCallDiscarded extends TLRPC$GroupCall {
    public static int constructor = 2004925620;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.duration = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.duration);
    }
}
