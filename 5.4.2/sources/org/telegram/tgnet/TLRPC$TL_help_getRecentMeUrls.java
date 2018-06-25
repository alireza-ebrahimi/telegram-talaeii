package org.telegram.tgnet;

public class TLRPC$TL_help_getRecentMeUrls extends TLObject {
    public static int constructor = 1036054804;
    public String referer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_help_recentMeUrls.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.referer);
    }
}
