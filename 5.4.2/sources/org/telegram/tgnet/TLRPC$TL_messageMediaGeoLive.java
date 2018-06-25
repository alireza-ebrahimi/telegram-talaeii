package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaGeoLive extends MessageMedia {
    public static int constructor = 2084316681;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.period = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.geo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.period);
    }
}
