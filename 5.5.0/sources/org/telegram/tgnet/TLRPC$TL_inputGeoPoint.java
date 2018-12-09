package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGeoPoint;

public class TLRPC$TL_inputGeoPoint extends InputGeoPoint {
    public static int constructor = -206066487;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.lat = abstractSerializedData.readDouble(z);
        this._long = abstractSerializedData.readDouble(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeDouble(this.lat);
        abstractSerializedData.writeDouble(this._long);
    }
}
