package org.telegram.tgnet;

public class TLRPC$TL_geochats_createGeoChat extends TLObject {
    public static int constructor = 235482646;
    public String address;
    public TLRPC$InputGeoPoint geo_point;
    public String title;
    public String venue;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.title);
        this.geo_point.serializeToStream(stream);
        stream.writeString(this.address);
        stream.writeString(this.venue);
    }
}
