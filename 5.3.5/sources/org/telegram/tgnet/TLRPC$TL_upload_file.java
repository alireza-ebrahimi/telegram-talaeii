package org.telegram.tgnet;

public class TLRPC$TL_upload_file extends TLRPC$upload_File {
    public static int constructor = 157948117;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.type = TLRPC$storage_FileType.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.mtime = stream.readInt32(exception);
        this.bytes = stream.readByteBuffer(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.type.serializeToStream(stream);
        stream.writeInt32(this.mtime);
        stream.writeByteBuffer(this.bytes);
    }

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }
}
