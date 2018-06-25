package org.telegram.tgnet;

public class TLRPC$TL_messages_getWebPage extends TLObject {
    public static int constructor = 852135825;
    public int hash;
    public String url;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$WebPage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeInt32(this.hash);
    }
}
