package org.telegram.tgnet;

public class TLRPC$TL_updateChatParticipants extends TLRPC$Update {
    public static int constructor = 125178264;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.participants = TLRPC$ChatParticipants.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.participants.serializeToStream(stream);
    }
}
