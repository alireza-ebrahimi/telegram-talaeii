package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantKicked_layer67 extends TLRPC$ChannelParticipant {
    public static int constructor = -1933187430;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.kicked_by = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.kicked_by);
        stream.writeInt32(this.date);
    }
}
