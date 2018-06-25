package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageMediaGeoPoint extends TLRPC$DecryptedMessageMedia {
    public static int constructor = 893913689;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.lat = stream.readDouble(exception);
        this._long = stream.readDouble(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeDouble(this.lat);
        stream.writeDouble(this._long);
    }
}
