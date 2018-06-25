package org.telegram.tgnet;

public class TLRPC$TL_inputMediaGifExternal extends TLRPC$InputMedia {
    public static int constructor = 1212395773;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.q = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeString(this.q);
    }
}
