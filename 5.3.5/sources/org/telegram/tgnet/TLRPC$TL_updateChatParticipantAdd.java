package org.telegram.tgnet;

public class TLRPC$TL_updateChatParticipantAdd extends TLRPC$Update {
    public static int constructor = -364179876;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.inviter_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.version = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.inviter_id);
        stream.writeInt32(this.date);
        stream.writeInt32(this.version);
    }
}
