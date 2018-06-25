package org.telegram.tgnet;

public class TLRPC$TL_geochats_getLocated extends TLObject {
    public static int constructor = 2132356495;
    public TLRPC$InputGeoPoint geo_point;
    public int limit;
    public int radius;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_geochats_located.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.geo_point.serializeToStream(stream);
        stream.writeInt32(this.radius);
        stream.writeInt32(this.limit);
    }
}
