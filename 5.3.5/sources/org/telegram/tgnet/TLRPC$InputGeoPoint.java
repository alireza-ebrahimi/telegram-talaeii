package org.telegram.tgnet;

public abstract class TLRPC$InputGeoPoint extends TLObject {
    public double _long;
    public double lat;

    public static TLRPC$InputGeoPoint TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputGeoPoint result = null;
        switch (constructor) {
            case -457104426:
                result = new TLRPC$TL_inputGeoPointEmpty();
                break;
            case -206066487:
                result = new TLRPC$TL_inputGeoPoint();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputGeoPoint", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
