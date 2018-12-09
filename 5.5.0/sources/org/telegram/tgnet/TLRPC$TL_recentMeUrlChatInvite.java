package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ChatInvite;
import org.telegram.tgnet.TLRPC.RecentMeUrl;

public class TLRPC$TL_recentMeUrlChatInvite extends RecentMeUrl {
    public static int constructor = -347535331;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
        this.chat_invite = ChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        this.chat_invite.serializeToStream(abstractSerializedData);
    }
}
