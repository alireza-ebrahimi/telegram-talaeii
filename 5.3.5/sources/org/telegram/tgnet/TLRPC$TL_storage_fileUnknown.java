package org.telegram.tgnet;

public class TLRPC$TL_storage_fileUnknown extends TLRPC$storage_FileType {
    public static int constructor = -1432995067;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
