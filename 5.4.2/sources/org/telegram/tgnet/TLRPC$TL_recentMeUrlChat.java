package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RecentMeUrl;

public class TLRPC$TL_recentMeUrlChat extends RecentMeUrl {
    public static int constructor = -1608834311;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
        this.chat_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeInt32(this.chat_id);
    }
}
