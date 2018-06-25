package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantsKicked extends TLRPC$ChannelParticipantsFilter {
    public static int constructor = -1548400251;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.q = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.q);
    }
}
