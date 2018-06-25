package org.telegram.tgnet;

public class TLRPC$TL_groupCallPrivate extends TLRPC$GroupCall {
    public static int constructor = 1829443076;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        if ((this.flags & 1) != 0) {
            this.channel_id = stream.readInt32(exception);
        }
        this.participants_count = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.channel_id);
        }
        stream.writeInt32(this.participants_count);
        stream.writeInt32(this.admin_id);
    }
}
