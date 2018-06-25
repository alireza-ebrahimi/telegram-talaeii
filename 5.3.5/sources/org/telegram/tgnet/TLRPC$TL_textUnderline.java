package org.telegram.tgnet;

public class TLRPC$TL_textUnderline extends TLRPC$RichText {
    public static int constructor = -1054465340;
    public TLRPC$RichText text;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.text.serializeToStream(stream);
    }
}
