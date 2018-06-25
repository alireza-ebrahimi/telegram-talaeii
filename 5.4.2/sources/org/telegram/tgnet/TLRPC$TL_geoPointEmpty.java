package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;

public class TLRPC$TL_geoPointEmpty extends GeoPoint {
    public static int constructor = 286776671;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
