package org.telegram.tgnet;

public class TLRPC$TL_messages_importChatInvite extends TLObject {
    public static int constructor = 1817183516;
    public String hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.hash);
    }
}
