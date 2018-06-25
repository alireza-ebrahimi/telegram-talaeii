package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEvent extends TLObject {
    public static int constructor = 995769920;
    public TLRPC$ChannelAdminLogEventAction action;
    public int date;
    public long id;
    public int user_id;

    public static TLRPC$TL_channelAdminLogEvent TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_channelAdminLogEvent result = new TLRPC$TL_channelAdminLogEvent();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_channelAdminLogEvent", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.action = TLRPC$ChannelAdminLogEventAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt32(this.date);
        stream.writeInt32(this.user_id);
        this.action.serializeToStream(stream);
    }
}
