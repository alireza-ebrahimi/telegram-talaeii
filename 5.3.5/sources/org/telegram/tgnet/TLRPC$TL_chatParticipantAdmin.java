package org.telegram.tgnet;

public class TLRPC$TL_chatParticipantAdmin extends TLRPC$ChatParticipant {
    public static int constructor = -489233354;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.inviter_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.inviter_id);
        stream.writeInt32(this.date);
    }
}
