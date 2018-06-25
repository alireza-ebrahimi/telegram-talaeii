package org.telegram.tgnet;

public class TLRPC$TL_encryptedFileEmpty extends TLRPC$EncryptedFile {
    public static int constructor = -1038136962;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
