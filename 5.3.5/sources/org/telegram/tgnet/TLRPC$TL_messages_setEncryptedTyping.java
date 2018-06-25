package org.telegram.tgnet;

public class TLRPC$TL_messages_setEncryptedTyping extends TLObject {
    public static int constructor = 2031374829;
    public TLRPC$TL_inputEncryptedChat peer;
    public boolean typing;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeBool(this.typing);
    }
}
