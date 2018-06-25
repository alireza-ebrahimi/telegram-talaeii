package org.telegram.tgnet;

public class TLRPC$TL_storage_filePng extends TLRPC$storage_FileType {
    public static int constructor = 172975040;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
