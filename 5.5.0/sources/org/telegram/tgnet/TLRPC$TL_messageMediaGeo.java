package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaGeo extends MessageMedia {
    public static int constructor = 1457575028;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.geo.serializeToStream(abstractSerializedData);
    }
}
