package org.telegram.tgnet;

public class TLRPC$TL_geoChat extends TLRPC$Chat {
    public static int constructor = 1978329690;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
        this.title = stream.readString(exception);
        this.address = stream.readString(exception);
        this.venue = stream.readString(exception);
        this.geo = TLRPC$GeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.participants_count = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.checked_in = stream.readBool(exception);
        this.version = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.title);
        stream.writeString(this.address);
        stream.writeString(this.venue);
        this.geo.serializeToStream(stream);
        this.photo.serializeToStream(stream);
        stream.writeInt32(this.participants_count);
        stream.writeInt32(this.date);
        stream.writeBool(this.checked_in);
        stream.writeInt32(this.version);
    }
}
