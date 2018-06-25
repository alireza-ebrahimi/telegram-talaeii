package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventActionTogglePreHistoryHidden extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = 1599903217;
    public boolean new_value;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.new_value = stream.readBool(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeBool(this.new_value);
    }
}
