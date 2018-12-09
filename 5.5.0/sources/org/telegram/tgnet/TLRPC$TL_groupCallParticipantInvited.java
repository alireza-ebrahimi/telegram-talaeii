package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCallParticipant;

public class TLRPC$TL_groupCallParticipantInvited extends GroupCallParticipant {
    public static int constructor = 930387696;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.inviter_id = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.phone_call = TLRPC$TL_inputPhoneCall.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt32(this.inviter_id);
        abstractSerializedData.writeInt32(this.date);
        if ((this.flags & 1) != 0) {
            this.phone_call.serializeToStream(abstractSerializedData);
        }
    }
}
