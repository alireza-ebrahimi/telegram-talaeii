package org.telegram.tgnet;

public class TLRPC$TL_textUrl extends TLRPC$RichText {
    public static int constructor = 1009288385;
    public TLRPC$RichText text;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.url = stream.readString(exception);
        this.webpage_id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.text.serializeToStream(stream);
        stream.writeString(this.url);
        stream.writeInt64(this.webpage_id);
    }
}
