package org.telegram.tgnet;

public class TLRPC$TL_messages_sendEncryptedService extends TLObject {
    public static int constructor = 852769188;
    public NativeByteBuffer data;
    public TLRPC$TL_inputEncryptedChat peer;
    public long random_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_SentEncryptedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt64(this.random_id);
        stream.writeByteBuffer(this.data);
    }

    public void freeResources() {
        if (this.data != null) {
            this.data.reuse();
            this.data = null;
        }
    }
}
