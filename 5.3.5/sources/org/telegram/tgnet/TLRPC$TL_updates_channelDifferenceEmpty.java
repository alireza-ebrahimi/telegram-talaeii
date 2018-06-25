package org.telegram.tgnet;

public class TLRPC$TL_updates_channelDifferenceEmpty extends TLRPC$updates_ChannelDifference {
    public static int constructor = 1041346555;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.isFinal = (this.flags & 1) != 0;
        this.pts = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            this.timeout = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.isFinal ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.pts);
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.timeout);
        }
    }
}
