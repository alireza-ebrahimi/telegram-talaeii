package org.telegram.tgnet;

public class TLRPC$TL_geoPointEmpty extends TLRPC$GeoPoint {
    public static int constructor = 286776671;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
