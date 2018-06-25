package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionDeleteMessage extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = 1121994683;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.message = TLRPC$Message.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.message.serializeToStream(stream);
    }
}
