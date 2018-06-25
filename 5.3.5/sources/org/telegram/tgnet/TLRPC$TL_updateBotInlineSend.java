package org.telegram.tgnet;

public class TLRPC$TL_updateBotInlineSend extends TLRPC$Update {
    public static int constructor = 239663460;
    public String id;
    public TLRPC$TL_inputBotInlineMessageID msg_id;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.query = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.geo = TLRPC$GeoPoint.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        this.id = stream.readString(exception);
        if ((this.flags & 2) != 0) {
            this.msg_id = TLRPC$TL_inputBotInlineMessageID.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt32(this.user_id);
        stream.writeString(this.query);
        if ((this.flags & 1) != 0) {
            this.geo.serializeToStream(stream);
        }
        stream.writeString(this.id);
        if ((this.flags & 2) != 0) {
            this.msg_id.serializeToStream(stream);
        }
    }
}
