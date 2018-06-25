package org.telegram.tgnet;

public class TLRPC$TL_updateReadChannelOutbox extends TLRPC$Update {
    public static int constructor = 634833351;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.channel_id = stream.readInt32(exception);
        this.max_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.channel_id);
        stream.writeInt32(this.max_id);
    }
}
