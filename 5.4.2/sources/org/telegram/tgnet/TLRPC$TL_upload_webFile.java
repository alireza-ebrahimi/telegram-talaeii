package org.telegram.tgnet;

public class TLRPC$TL_upload_webFile extends TLObject {
    public static int constructor = 568808380;
    public NativeByteBuffer bytes;
    public TLRPC$storage_FileType file_type;
    public String mime_type;
    public int mtime;
    public int size;

    public static TLRPC$TL_upload_webFile TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_upload_webFile tLRPC$TL_upload_webFile = new TLRPC$TL_upload_webFile();
            tLRPC$TL_upload_webFile.readParams(abstractSerializedData, z);
            return tLRPC$TL_upload_webFile;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_upload_webFile", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.size = abstractSerializedData.readInt32(z);
        this.mime_type = abstractSerializedData.readString(z);
        this.file_type = TLRPC$storage_FileType.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.mtime = abstractSerializedData.readInt32(z);
        this.bytes = abstractSerializedData.readByteBuffer(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.size);
        abstractSerializedData.writeString(this.mime_type);
        this.file_type.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.mtime);
        abstractSerializedData.writeByteBuffer(this.bytes);
    }
}
