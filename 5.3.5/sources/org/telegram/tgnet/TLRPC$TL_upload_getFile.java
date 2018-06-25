package org.telegram.tgnet;

public class TLRPC$TL_upload_getFile extends TLObject {
    public static int constructor = -475607115;
    public int limit;
    public TLRPC$InputFileLocation location;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$upload_File.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.location.serializeToStream(stream);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.limit);
    }
}
