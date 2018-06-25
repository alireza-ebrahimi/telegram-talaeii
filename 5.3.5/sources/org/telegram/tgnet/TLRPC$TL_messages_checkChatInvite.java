package org.telegram.tgnet;

public class TLRPC$TL_messages_checkChatInvite extends TLObject {
    public static int constructor = 1051570619;
    public String hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$ChatInvite.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.hash);
    }
}
