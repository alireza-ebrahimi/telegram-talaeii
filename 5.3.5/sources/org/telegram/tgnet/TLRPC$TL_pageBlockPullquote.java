package org.telegram.tgnet;

public class TLRPC$TL_pageBlockPullquote extends TLRPC$PageBlock {
    public static int constructor = 1329878739;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.caption = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.text.serializeToStream(stream);
        this.caption.serializeToStream(stream);
    }
}
