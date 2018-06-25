package org.telegram.tgnet;

public class TLRPC$TL_chatParticipantsForbidden_old extends TLRPC$TL_chatParticipantsForbidden {
    public static int constructor = 265468810;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
    }
}
