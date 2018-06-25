package org.telegram.tgnet;

public class TLRPC$TL_phone_discardCall extends TLObject {
    public static int constructor = 2027164582;
    public long connection_id;
    public int duration;
    public TLRPC$TL_inputPhoneCall peer;
    public TLRPC$PhoneCallDiscardReason reason;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.duration);
        this.reason.serializeToStream(stream);
        stream.writeInt64(this.connection_id);
    }
}
