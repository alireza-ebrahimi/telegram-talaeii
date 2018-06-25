package org.telegram.tgnet;

public class TLRPC$TL_messageMediaGeo extends TLRPC$MessageMedia {
    public static int constructor = 1457575028;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.geo = TLRPC$GeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.geo.serializeToStream(stream);
    }
}
