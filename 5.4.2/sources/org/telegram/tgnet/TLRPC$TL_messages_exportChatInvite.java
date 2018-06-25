package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ExportedChatInvite;

public class TLRPC$TL_messages_exportChatInvite extends TLObject {
    public static int constructor = 2106086025;
    public int chat_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return ExportedChatInvite.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
    }
}
