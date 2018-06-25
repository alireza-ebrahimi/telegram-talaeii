package org.telegram.tgnet;

public class TLRPC$TL_phoneCallDiscarded extends TLRPC$PhoneCall {
    public static int constructor = 1355435489;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z = true;
        this.flags = stream.readInt32(exception);
        this.need_rating = (this.flags & 4) != 0;
        if ((this.flags & 8) == 0) {
            z = false;
        }
        this.need_debug = z;
        this.id = stream.readInt64(exception);
        if ((this.flags & 1) != 0) {
            this.reason = TLRPC$PhoneCallDiscardReason.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2) != 0) {
            this.duration = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.need_rating ? this.flags | 4 : this.flags & -5;
        this.flags = this.need_debug ? this.flags | 8 : this.flags & -9;
        stream.writeInt32(this.flags);
        stream.writeInt64(this.id);
        if ((this.flags & 1) != 0) {
            this.reason.serializeToStream(stream);
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.duration);
        }
    }
}
