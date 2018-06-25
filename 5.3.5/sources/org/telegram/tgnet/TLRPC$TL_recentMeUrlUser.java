package org.telegram.tgnet;

public class TLRPC$TL_recentMeUrlUser extends TLRPC$RecentMeUrl {
    public static int constructor = -1917045962;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.user_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeInt32(this.user_id);
    }
}
