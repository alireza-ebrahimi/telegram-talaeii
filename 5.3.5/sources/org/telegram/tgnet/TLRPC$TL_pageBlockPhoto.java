package org.telegram.tgnet;

public class TLRPC$TL_pageBlockPhoto extends TLRPC$PageBlock {
    public static int constructor = -372860542;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.photo_id = stream.readInt64(exception);
        this.caption = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.photo_id);
        this.caption.serializeToStream(stream);
    }
}
