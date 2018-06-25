package org.telegram.tgnet;

public class TLRPC$TL_chatParticipantCreator extends TLRPC$ChatParticipant {
    public static int constructor = -636267638;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
    }
}
