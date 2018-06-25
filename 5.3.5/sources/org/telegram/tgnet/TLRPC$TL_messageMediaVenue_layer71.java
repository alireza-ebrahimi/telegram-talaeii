package org.telegram.tgnet;

public class TLRPC$TL_messageMediaVenue_layer71 extends TLRPC$MessageMedia {
    public static int constructor = 2031269663;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.geo = TLRPC$GeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.title = stream.readString(exception);
        this.address = stream.readString(exception);
        this.provider = stream.readString(exception);
        this.venue_id = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.geo.serializeToStream(stream);
        stream.writeString(this.title);
        stream.writeString(this.address);
        stream.writeString(this.provider);
        stream.writeString(this.venue_id);
    }
}
