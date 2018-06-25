package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedFile;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;

public class TLRPC$TL_messages_uploadEncryptedFile extends TLObject {
    public static int constructor = 1347929239;
    public InputEncryptedFile file;
    public TLRPC$TL_inputEncryptedChat peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return EncryptedFile.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.file.serializeToStream(abstractSerializedData);
    }
}
