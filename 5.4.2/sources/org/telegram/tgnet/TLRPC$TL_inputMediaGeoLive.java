package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGeoPoint;
import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaGeoLive extends InputMedia {
    public static int constructor = 2065305999;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.geo_point = InputGeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.period = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.geo_point.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.period);
    }
}
