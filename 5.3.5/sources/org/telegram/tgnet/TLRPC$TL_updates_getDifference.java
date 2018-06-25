package org.telegram.tgnet;

public class TLRPC$TL_updates_getDifference extends TLObject {
    public static int constructor = 630429265;
    public int date;
    public int flags;
    public int pts;
    public int pts_total_limit;
    public int qts;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$updates_Difference.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt32(this.pts);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.pts_total_limit);
        }
        stream.writeInt32(this.date);
        stream.writeInt32(this.qts);
    }
}
