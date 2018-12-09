package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputEncryptedFile;

public class TLRPC$TL_messages_sendEncryptedFile extends TLObject {
    public static int constructor = -1701831834;
    public NativeByteBuffer data;
    public InputEncryptedFile file;
    public TLRPC$TL_inputEncryptedChat peer;
    public long random_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_SentEncryptedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void freeResources() {
        if (this.data != null) {
            this.data.reuse();
            this.data = null;
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt64(this.random_id);
        abstractSerializedData.writeByteBuffer(this.data);
        this.file.serializeToStream(abstractSerializedData);
    }
}
