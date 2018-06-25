package org.telegram.tgnet;

public class TLRPC$TL_pageBlockTitle extends TLRPC$PageBlock {
    public static int constructor = 1890305021;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.text.serializeToStream(stream);
    }
}
