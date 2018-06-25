package org.telegram.tgnet;

public class TLRPC$TL_pageBlockAuthorDate_layer60 extends TLRPC$TL_pageBlockAuthorDate {
    public static int constructor = 1029399794;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        String authorString = stream.readString(exception);
        this.author = new TLRPC$TL_textPlain();
        ((TLRPC$TL_textPlain) this.author).text = authorString;
        this.published_date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(((TLRPC$TL_textPlain) this.author).text);
        stream.writeInt32(this.published_date);
    }
}
