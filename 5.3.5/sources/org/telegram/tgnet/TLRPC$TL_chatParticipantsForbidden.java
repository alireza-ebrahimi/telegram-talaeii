package org.telegram.tgnet;

public class TLRPC$TL_chatParticipantsForbidden extends TLRPC$ChatParticipants {
    public static int constructor = -57668565;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.chat_id = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.self_participant = TLRPC$ChatParticipant.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt32(this.chat_id);
        if ((this.flags & 1) != 0) {
            this.self_participant.serializeToStream(stream);
        }
    }
}
