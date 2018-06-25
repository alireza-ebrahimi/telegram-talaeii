package org.telegram.tgnet;

public class TLRPC$TL_inputMediaGeoLive extends TLRPC$InputMedia {
    public static int constructor = 2065305999;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.geo_point = TLRPC$InputGeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.period = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.geo_point.serializeToStream(stream);
        stream.writeInt32(this.period);
    }
}
