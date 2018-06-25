package org.telegram.tgnet;

public class TLRPC$TL_upload_getCdnFile extends TLObject {
    public static int constructor = 536919235;
    public byte[] file_token;
    public int limit;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$upload_CdnFile.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.file_token);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.limit);
    }
}
