package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGeoPoint;

public class TLRPC$TL_geochats_getLocated extends TLObject {
    public static int constructor = 2132356495;
    public InputGeoPoint geo_point;
    public int limit;
    public int radius;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_geochats_located.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.geo_point.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.radius);
        abstractSerializedData.writeInt32(this.limit);
    }
}
