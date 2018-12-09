package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ChatInvite;

public class TLRPC$TL_messages_checkChatInvite extends TLObject {
    public static int constructor = 1051570619;
    public String hash;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return ChatInvite.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.hash);
    }
}
