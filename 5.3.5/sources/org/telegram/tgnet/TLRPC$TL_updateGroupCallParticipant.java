package org.telegram.tgnet;

public class TLRPC$TL_updateGroupCallParticipant extends TLRPC$Update {
    public static int constructor = 92188360;
    public TLRPC$TL_inputGroupCall call;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.call = TLRPC$TL_inputGroupCall.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.participant = TLRPC$GroupCallParticipant.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.call.serializeToStream(stream);
        this.participant.serializeToStream(stream);
    }
}
