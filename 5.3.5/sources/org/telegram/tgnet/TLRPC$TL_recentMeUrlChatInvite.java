package org.telegram.tgnet;

public class TLRPC$TL_recentMeUrlChatInvite extends TLRPC$RecentMeUrl {
    public static int constructor = -347535331;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.chat_invite = TLRPC$ChatInvite.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        this.chat_invite.serializeToStream(stream);
    }
}
