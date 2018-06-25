package org.telegram.tgnet;

public class TLRPC$TL_groupCallParticipantInvited extends TLRPC$GroupCallParticipant {
    public static int constructor = 930387696;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.inviter_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.phone_call = TLRPC$TL_inputPhoneCall.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.inviter_id);
        stream.writeInt32(this.date);
        if ((this.flags & 1) != 0) {
            this.phone_call.serializeToStream(stream);
        }
    }
}
