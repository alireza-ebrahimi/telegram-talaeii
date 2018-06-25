package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionParticipantJoin extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = 405815507;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
