package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaVenue_layer71 extends MessageMedia {
    public static int constructor = 2031269663;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.title = abstractSerializedData.readString(z);
        this.address = abstractSerializedData.readString(z);
        this.provider = abstractSerializedData.readString(z);
        this.venue_id = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.geo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.address);
        abstractSerializedData.writeString(this.provider);
        abstractSerializedData.writeString(this.venue_id);
    }
}
