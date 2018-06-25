package org.telegram.tgnet;

public class TLRPC$TL_messageMediaGeoLive extends TLRPC$MessageMedia {
    public static int constructor = 2084316681;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.geo = TLRPC$GeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.period = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.geo.serializeToStream(stream);
        stream.writeInt32(this.period);
    }
}
