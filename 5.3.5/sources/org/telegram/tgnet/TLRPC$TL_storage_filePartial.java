package org.telegram.tgnet;

public class TLRPC$TL_storage_filePartial extends TLRPC$storage_FileType {
    public static int constructor = 1086091090;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
