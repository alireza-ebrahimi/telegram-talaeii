package org.telegram.tgnet;

public class TLRPC$TL_help_getRecentMeUrls extends TLObject {
    public static int constructor = 1036054804;
    public String referer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_help_recentMeUrls.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.referer);
    }
}
