package org.telegram.tgnet;

public class TLRPC$TL_messages_getWebPagePreview extends TLObject {
    public static int constructor = 623001124;
    public String message;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$MessageMedia.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.message);
    }
}
