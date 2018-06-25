package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionTyping extends TLRPC$DecryptedMessageAction {
    public static int constructor = -860719551;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.action = TLRPC$SendMessageAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.action.serializeToStream(stream);
    }
}
