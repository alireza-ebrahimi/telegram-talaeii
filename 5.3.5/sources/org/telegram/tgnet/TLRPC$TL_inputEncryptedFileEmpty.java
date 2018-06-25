package org.telegram.tgnet;

public class TLRPC$TL_inputEncryptedFileEmpty extends TLRPC$InputEncryptedFile {
    public static int constructor = 406307684;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
