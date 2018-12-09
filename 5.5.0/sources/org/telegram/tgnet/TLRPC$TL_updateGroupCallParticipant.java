package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCallParticipant;

public class TLRPC$TL_updateGroupCallParticipant extends TLRPC$Update {
    public static int constructor = 92188360;
    public TLRPC$TL_inputGroupCall call;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.call = TLRPC$TL_inputGroupCall.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.participant = GroupCallParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.call.serializeToStream(abstractSerializedData);
        this.participant.serializeToStream(abstractSerializedData);
    }
}
