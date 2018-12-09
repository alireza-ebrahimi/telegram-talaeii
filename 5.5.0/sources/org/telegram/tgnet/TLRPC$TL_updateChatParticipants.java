package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ChatParticipants;

public class TLRPC$TL_updateChatParticipants extends TLRPC$Update {
    public static int constructor = 125178264;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.participants = ChatParticipants.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.participants.serializeToStream(abstractSerializedData);
    }
}
