package org.telegram.tgnet;

public class TLRPC$TL_recentMeUrlChat extends TLRPC$RecentMeUrl {
    public static int constructor = -1608834311;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.chat_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeInt32(this.chat_id);
    }
}
