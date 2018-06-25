package org.telegram.tgnet;

public class TLRPC$TL_messageMediaDocument_old extends TLRPC$TL_messageMediaDocument {
    public static int constructor = 802824708;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.document = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.document.serializeToStream(stream);
    }
}
