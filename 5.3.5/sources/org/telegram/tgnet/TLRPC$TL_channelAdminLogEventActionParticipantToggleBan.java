package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionParticipantToggleBan extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = -422036098;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.prev_participant = TLRPC$ChannelParticipant.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.new_participant = TLRPC$ChannelParticipant.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.prev_participant.serializeToStream(stream);
        this.new_participant.serializeToStream(stream);
    }
}
