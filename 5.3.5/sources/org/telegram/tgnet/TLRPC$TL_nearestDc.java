package org.telegram.tgnet;

public class TLRPC$TL_nearestDc extends TLObject {
    public static int constructor = -1910892683;
    public String country;
    public int nearest_dc;
    public int this_dc;

    public static TLRPC$TL_nearestDc TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_nearestDc result = new TLRPC$TL_nearestDc();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_nearestDc", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.country = stream.readString(exception);
        this.this_dc = stream.readInt32(exception);
        this.nearest_dc = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.country);
        stream.writeInt32(this.this_dc);
        stream.writeInt32(this.nearest_dc);
    }
}
