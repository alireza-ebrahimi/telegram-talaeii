package org.telegram.tgnet;

public class TLRPC$TL_storage_fileMp4 extends TLRPC$storage_FileType {
    public static int constructor = -1278304028;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
