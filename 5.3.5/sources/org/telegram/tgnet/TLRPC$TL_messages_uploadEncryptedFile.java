package org.telegram.tgnet;

public class TLRPC$TL_messages_uploadEncryptedFile extends TLObject {
    public static int constructor = 1347929239;
    public TLRPC$InputEncryptedFile file;
    public TLRPC$TL_inputEncryptedChat peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$EncryptedFile.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        this.file.serializeToStream(stream);
    }
}
