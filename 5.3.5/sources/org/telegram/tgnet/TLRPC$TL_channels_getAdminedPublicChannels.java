package org.telegram.tgnet;

public class TLRPC$TL_channels_getAdminedPublicChannels extends TLObject {
    public static int constructor = -1920105769;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Chats.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
