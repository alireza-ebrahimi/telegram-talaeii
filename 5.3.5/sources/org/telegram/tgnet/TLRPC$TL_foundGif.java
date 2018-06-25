package org.telegram.tgnet;

public class TLRPC$TL_foundGif extends TLRPC$FoundGif {
    public static int constructor = 372165663;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.thumb_url = stream.readString(exception);
        this.content_url = stream.readString(exception);
        this.content_type = stream.readString(exception);
        this.w = stream.readInt32(exception);
        this.h = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeString(this.thumb_url);
        stream.writeString(this.content_url);
        stream.writeString(this.content_type);
        stream.writeInt32(this.w);
        stream.writeInt32(this.h);
    }
}
