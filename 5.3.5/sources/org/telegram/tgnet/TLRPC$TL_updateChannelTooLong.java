package org.telegram.tgnet;

public class TLRPC$TL_updateChannelTooLong extends TLRPC$Update {
    public static int constructor = -352032773;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.channel_id = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.pts = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt32(this.channel_id);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.pts);
        }
    }
}
