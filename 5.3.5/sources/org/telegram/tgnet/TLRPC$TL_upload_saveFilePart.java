package org.telegram.tgnet;

public class TLRPC$TL_upload_saveFilePart extends TLObject {
    public static int constructor = -1291540959;
    public NativeByteBuffer bytes;
    public long file_id;
    public int file_part;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.file_id);
        stream.writeInt32(this.file_part);
        stream.writeByteBuffer(this.bytes);
    }

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }
}
