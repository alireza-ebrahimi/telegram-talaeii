package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedFile;

public class TLRPC$TL_encryptedFileEmpty extends EncryptedFile {
    public static int constructor = -1038136962;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
