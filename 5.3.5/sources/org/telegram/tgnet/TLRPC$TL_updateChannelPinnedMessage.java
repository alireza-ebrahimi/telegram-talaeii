package org.telegram.tgnet;

public class TLRPC$TL_updateChannelPinnedMessage extends TLRPC$Update {
    public static int constructor = -1738988427;
    public int id;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.channel_id = stream.readInt32(exception);
        this.id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.channel_id);
        stream.writeInt32(this.id);
    }
}
