package org.telegram.tgnet;

public class TLRPC$TL_pageBlockPreformatted extends TLRPC$PageBlock {
    public static int constructor = -1066346178;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.language = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.text.serializeToStream(stream);
        stream.writeString(this.language);
    }
}
