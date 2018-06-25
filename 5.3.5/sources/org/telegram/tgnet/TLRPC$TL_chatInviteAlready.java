package org.telegram.tgnet;

public class TLRPC$TL_chatInviteAlready extends TLRPC$ChatInvite {
    public static int constructor = 1516793212;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.chat.serializeToStream(stream);
    }
}
