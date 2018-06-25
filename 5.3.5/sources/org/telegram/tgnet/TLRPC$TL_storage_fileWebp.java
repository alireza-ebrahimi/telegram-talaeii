package org.telegram.tgnet;

public class TLRPC$TL_storage_fileWebp extends TLRPC$storage_FileType {
    public static int constructor = 276907596;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
