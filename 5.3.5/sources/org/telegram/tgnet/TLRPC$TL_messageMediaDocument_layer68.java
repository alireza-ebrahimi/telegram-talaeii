package org.telegram.tgnet;

public class TLRPC$TL_messageMediaDocument_layer68 extends TLRPC$TL_messageMediaDocument {
    public static int constructor = -203411800;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.document = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.caption = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.document.serializeToStream(stream);
        stream.writeString(this.caption);
    }
}
