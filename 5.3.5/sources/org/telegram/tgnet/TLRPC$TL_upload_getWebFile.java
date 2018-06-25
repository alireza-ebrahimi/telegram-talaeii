package org.telegram.tgnet;

public class TLRPC$TL_upload_getWebFile extends TLObject {
    public static int constructor = 619086221;
    public int limit;
    public TLRPC$TL_inputWebFileLocation location;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_upload_webFile.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.location.serializeToStream(stream);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.limit);
    }
}
