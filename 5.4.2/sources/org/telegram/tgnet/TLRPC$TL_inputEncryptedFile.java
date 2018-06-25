package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputEncryptedFile;

public class TLRPC$TL_inputEncryptedFile extends InputEncryptedFile {
    public static int constructor = 1511503333;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
    }
}
