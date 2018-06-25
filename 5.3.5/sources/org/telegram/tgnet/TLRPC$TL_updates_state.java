package org.telegram.tgnet;

public class TLRPC$TL_updates_state extends TLObject {
    public static int constructor = -1519637954;
    public int date;
    public int pts;
    public int qts;
    public int seq;
    public int unread_count;

    public static TLRPC$TL_updates_state TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_updates_state result = new TLRPC$TL_updates_state();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_updates_state", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.pts = stream.readInt32(exception);
        this.qts = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.seq = stream.readInt32(exception);
        this.unread_count = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.pts);
        stream.writeInt32(this.qts);
        stream.writeInt32(this.date);
        stream.writeInt32(this.seq);
        stream.writeInt32(this.unread_count);
    }
}
