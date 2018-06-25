package org.telegram.tgnet;

public class TLRPC$TL_phoneCallWaiting extends TLRPC$PhoneCall {
    public static int constructor = 462375633;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
        this.participant_id = stream.readInt32(exception);
        this.protocol = TLRPC$TL_phoneCallProtocol.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 1) != 0) {
            this.receive_date = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.date);
        stream.writeInt32(this.admin_id);
        stream.writeInt32(this.participant_id);
        this.protocol.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.receive_date);
        }
    }
}
