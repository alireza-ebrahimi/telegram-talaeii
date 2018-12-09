package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputEncryptedFile;

public class TLRPC$TL_inputEncryptedFileEmpty extends InputEncryptedFile {
    public static int constructor = 406307684;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
