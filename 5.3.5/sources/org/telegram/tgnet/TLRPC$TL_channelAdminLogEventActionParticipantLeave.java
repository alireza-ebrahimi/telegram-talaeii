package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionParticipantLeave extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = -124291086;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
