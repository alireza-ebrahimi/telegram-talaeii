package org.telegram.tgnet;

public class TLRPC$TL_inputEncryptedFileBigUploaded extends TLRPC$InputEncryptedFile {
    public static int constructor = 767652808;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.parts = stream.readInt32(exception);
        this.key_fingerprint = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt32(this.parts);
        stream.writeInt32(this.key_fingerprint);
    }
}
