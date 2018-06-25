package org.telegram.tgnet;

public class TLRPC$TL_inputMediaGeoPoint extends TLRPC$InputMedia {
    public static int constructor = -104578748;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.geo_point = TLRPC$InputGeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.geo_point.serializeToStream(stream);
    }
}
