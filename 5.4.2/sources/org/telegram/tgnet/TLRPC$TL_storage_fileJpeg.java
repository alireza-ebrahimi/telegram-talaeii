package org.telegram.tgnet;

public class TLRPC$TL_storage_fileJpeg extends TLRPC$storage_FileType {
    public static int constructor = 8322574;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
