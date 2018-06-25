package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCallParticipant;

public class TLRPC$TL_groupCallParticipantAdmin extends GroupCallParticipant {
    public static int constructor = 1326135736;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
        this.member_tag_hash = abstractSerializedData.readByteArray(z);
        this.streams = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeByteArray(this.member_tag_hash);
        abstractSerializedData.writeByteArray(this.streams);
    }
}
