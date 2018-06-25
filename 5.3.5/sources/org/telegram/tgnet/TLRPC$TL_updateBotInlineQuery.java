package org.telegram.tgnet;

public class TLRPC$TL_updateBotInlineQuery extends TLRPC$Update {
    public static int constructor = 1417832080;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.query_id = stream.readInt64(exception);
        this.user_id = stream.readInt32(exception);
        this.query = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.geo = TLRPC$GeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        this.offset = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.query_id);
        stream.writeInt32(this.user_id);
        stream.writeString(this.query);
        if ((this.flags & 1) != 0) {
            this.geo.serializeToStream(stream);
        }
        stream.writeString(this.offset);
    }
}
