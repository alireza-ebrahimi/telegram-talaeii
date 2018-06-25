package org.telegram.tgnet;

public class TLRPC$TL_updates_channelDifferenceEmpty extends TLRPC$updates_ChannelDifference {
    public static int constructor = 1041346555;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.isFinal = (this.flags & 1) != 0;
        this.pts = abstractSerializedData.readInt32(z);
        if ((this.flags & 2) != 0) {
            this.timeout = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.isFinal ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.pts);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.timeout);
        }
    }
}
