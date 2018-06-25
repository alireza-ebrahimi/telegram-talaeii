package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGeoPoint;

public class TLRPC$TL_geochats_createGeoChat extends TLObject {
    public static int constructor = 235482646;
    public String address;
    public InputGeoPoint geo_point;
    public String title;
    public String venue;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.title);
        this.geo_point.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.address);
        abstractSerializedData.writeString(this.venue);
    }
}
