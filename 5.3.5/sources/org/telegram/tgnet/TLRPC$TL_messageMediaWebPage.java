package org.telegram.tgnet;

public class TLRPC$TL_messageMediaWebPage extends TLRPC$MessageMedia {
    public static int constructor = -1557277184;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.webpage = TLRPC$WebPage.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.webpage.serializeToStream(stream);
    }
}
