package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGeoPoint;
import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaGeoPoint extends InputMedia {
    public static int constructor = -104578748;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.geo_point = InputGeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.geo_point.serializeToStream(abstractSerializedData);
    }
}
