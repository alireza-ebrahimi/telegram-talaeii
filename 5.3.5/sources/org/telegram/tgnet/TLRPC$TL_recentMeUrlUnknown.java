package org.telegram.tgnet;

public class TLRPC$TL_recentMeUrlUnknown extends TLRPC$RecentMeUrl {
    public static int constructor = 1189204285;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
    }
}
