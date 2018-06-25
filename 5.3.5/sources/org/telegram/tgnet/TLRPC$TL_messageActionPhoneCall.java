package org.telegram.tgnet;

public class TLRPC$TL_messageActionPhoneCall extends TLRPC$MessageAction {
    public static int constructor = -2132731265;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.call_id = stream.readInt64(exception);
        if ((this.flags & 1) != 0) {
            this.reason = TLRPC$PhoneCallDiscardReason.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2) != 0) {
            this.duration = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.call_id);
        if ((this.flags & 1) != 0) {
            this.reason.serializeToStream(stream);
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.duration);
        }
    }
}
