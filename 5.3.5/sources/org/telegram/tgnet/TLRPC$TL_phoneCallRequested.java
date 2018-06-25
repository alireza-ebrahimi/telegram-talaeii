package org.telegram.tgnet;

public class TLRPC$TL_phoneCallRequested extends TLRPC$PhoneCall {
    public static int constructor = -2089411356;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
        this.participant_id = stream.readInt32(exception);
        this.g_a_hash = stream.readByteArray(exception);
        this.protocol = TLRPC$TL_phoneCallProtocol.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.date);
        stream.writeInt32(this.admin_id);
        stream.writeInt32(this.participant_id);
        stream.writeByteArray(this.g_a_hash);
        this.protocol.serializeToStream(stream);
    }
}
