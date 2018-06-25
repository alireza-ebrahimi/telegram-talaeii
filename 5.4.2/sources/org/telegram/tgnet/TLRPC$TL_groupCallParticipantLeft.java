package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCallParticipant;

public class TLRPC$TL_groupCallParticipantLeft extends GroupCallParticipant {
    public static int constructor = 1100680690;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
    }
}
