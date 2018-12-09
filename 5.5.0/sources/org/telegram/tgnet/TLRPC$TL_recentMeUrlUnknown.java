package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RecentMeUrl;

public class TLRPC$TL_recentMeUrlUnknown extends RecentMeUrl {
    public static int constructor = 1189204285;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
    }
}
