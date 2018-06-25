package org.telegram.tgnet;

public class TLRPC$TL_upload_webFile extends TLObject {
    public static int constructor = 568808380;
    public NativeByteBuffer bytes;
    public TLRPC$storage_FileType file_type;
    public String mime_type;
    public int mtime;
    public int size;

    public static TLRPC$TL_upload_webFile TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_upload_webFile result = new TLRPC$TL_upload_webFile();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_upload_webFile", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.size = stream.readInt32(exception);
        this.mime_type = stream.readString(exception);
        this.file_type = TLRPC$storage_FileType.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.mtime = stream.readInt32(exception);
        this.bytes = stream.readByteBuffer(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.size);
        stream.writeString(this.mime_type);
        this.file_type.serializeToStream(stream);
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
