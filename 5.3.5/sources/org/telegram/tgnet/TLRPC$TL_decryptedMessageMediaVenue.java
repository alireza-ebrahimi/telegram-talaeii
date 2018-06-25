package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageMediaVenue extends TLRPC$DecryptedMessageMedia {
    public static int constructor = -1978796689;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.lat = stream.readDouble(exception);
        this._long = stream.readDouble(exception);
        this.title = stream.readString(exception);
        this.address = stream.readString(exception);
        this.provider = stream.readString(exception);
        this.venue_id = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeDouble(this.lat);
        stream.writeDouble(this._long);
        stream.writeString(this.title);
        stream.writeString(this.address);
        stream.writeString(this.provider);
        stream.writeString(this.venue_id);
    }
}
