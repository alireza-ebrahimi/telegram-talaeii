package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantCreator extends TLRPC$ChannelParticipant {
    public static int constructor = -471670279;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
    }
}
