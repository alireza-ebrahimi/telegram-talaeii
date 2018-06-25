package org.telegram.tgnet;

public class TLRPC$TL_storage_filePdf extends TLRPC$storage_FileType {
    public static int constructor = -1373745011;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
