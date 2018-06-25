package org.telegram.tgnet;

public class TLRPC$TL_contactBlocked extends TLObject {
    public static int constructor = 1444661369;
    public int date;
    public int user_id;

    public static TLRPC$TL_contactBlocked TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_contactBlocked result = new TLRPC$TL_contactBlocked();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contactBlocked", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.date);
    }
}
