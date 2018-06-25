package org.telegram.tgnet;

public class TLRPC$TL_messages_getDocumentByHash extends TLObject {
    public static int constructor = 864953444;
    public String mime_type;
    public byte[] sha256;
    public int size;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Document.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.sha256);
        stream.writeInt32(this.size);
        stream.writeString(this.mime_type);
    }
}
