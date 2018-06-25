package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCallParticipant;

public class TLRPC$TL_groupCallParticipant extends GroupCallParticipant {
    public static int constructor = 1486730135;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.readonly = (this.flags & 1) != 0;
        this.user_id = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.member_tag_hash = abstractSerializedData.readByteArray(z);
        this.streams = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.readonly ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeByteArray(this.member_tag_hash);
        abstractSerializedData.writeByteArray(this.streams);
    }
}
