package org.telegram.tgnet;

public class TLRPC$TL_upload_saveBigFilePart extends TLObject {
    public static int constructor = -562337987;
    public NativeByteBuffer bytes;
    public long file_id;
    public int file_part;
    public int file_total_parts;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.file_id);
        stream.writeInt32(this.file_part);
        stream.writeInt32(this.file_total_parts);
        stream.writeByteBuffer(this.bytes);
    }

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }
}
