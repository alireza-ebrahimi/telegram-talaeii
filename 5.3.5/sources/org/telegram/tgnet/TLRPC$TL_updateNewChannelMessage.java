package org.telegram.tgnet;

public class TLRPC$TL_updateNewChannelMessage extends TLRPC$Update {
    public static int constructor = 1656358105;
    public TLRPC$Message message;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.message = TLRPC$Message.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.pts = stream.readInt32(exception);
        this.pts_count = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.message.serializeToStream(stream);
        stream.writeInt32(this.pts);
        stream.writeInt32(this.pts_count);
    }
}
