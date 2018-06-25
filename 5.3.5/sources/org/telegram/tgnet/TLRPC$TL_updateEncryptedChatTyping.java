package org.telegram.tgnet;

public class TLRPC$TL_updateEncryptedChatTyping extends TLRPC$Update {
    public static int constructor = 386986326;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
    }
}
