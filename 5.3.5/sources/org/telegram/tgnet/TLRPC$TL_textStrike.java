package org.telegram.tgnet;

public class TLRPC$TL_textStrike extends TLRPC$RichText {
    public static int constructor = -1678197867;
    public TLRPC$RichText text;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.text.serializeToStream(stream);
    }
}
