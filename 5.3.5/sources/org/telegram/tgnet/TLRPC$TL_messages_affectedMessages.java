package org.telegram.tgnet;

public class TLRPC$TL_messages_affectedMessages extends TLObject {
    public static int constructor = -2066640507;
    public int pts;
    public int pts_count;

    public static TLRPC$TL_messages_affectedMessages TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_affectedMessages result = new TLRPC$TL_messages_affectedMessages();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_affectedMessages", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.pts = stream.readInt32(exception);
        this.pts_count = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.pts);
        stream.writeInt32(this.pts_count);
    }
}
