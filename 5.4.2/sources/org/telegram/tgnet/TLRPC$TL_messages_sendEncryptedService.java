package org.telegram.tgnet;

public class TLRPC$TL_messages_sendEncryptedService extends TLObject {
    public static int constructor = 852769188;
    public NativeByteBuffer data;
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
    }
}
