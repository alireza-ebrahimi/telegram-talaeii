package org.telegram.tgnet;

public class TLRPC$TL_messageService_old extends TLRPC$TL_messageService {
    public static int constructor = -1618124613;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.from_id = stream.readInt32(exception);
        this.to_id = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.out = stream.readBool(exception);
        this.unread = stream.readBool(exception);
        this.flags |= 256;
        this.date = stream.readInt32(exception);
        this.action = TLRPC$MessageAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt32(this.from_id);
        this.to_id.serializeToStream(stream);
        stream.writeBool(this.out);
        stream.writeBool(this.unread);
        stream.writeInt32(this.date);
        this.action.serializeToStream(stream);
    }
}
