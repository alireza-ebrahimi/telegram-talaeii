package org.telegram.tgnet;

public class TLRPC$TL_geoPoint extends TLRPC$GeoPoint {
    public static int constructor = 541710092;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this._long = stream.readDouble(exception);
        this.lat = stream.readDouble(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeDouble(this._long);
        stream.writeDouble(this.lat);
    }
}
