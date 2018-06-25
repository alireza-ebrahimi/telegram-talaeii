package org.telegram.tgnet;

public abstract class TLRPC$GeoPoint extends TLObject {
    public double _long;
    public double lat;

    public static TLRPC$GeoPoint TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$GeoPoint result = null;
        switch (constructor) {
            case 286776671:
                result = new TLRPC$TL_geoPointEmpty();
                break;
            case 541710092:
                result = new TLRPC$TL_geoPoint();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in GeoPoint", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
