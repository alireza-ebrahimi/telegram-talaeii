package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;

public class TLRPC$TL_geoPoint extends GeoPoint {
    public static int constructor = 541710092;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this._long = abstractSerializedData.readDouble(z);
        this.lat = abstractSerializedData.readDouble(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeDouble(this._long);
        abstractSerializedData.writeDouble(this.lat);
    }
}
