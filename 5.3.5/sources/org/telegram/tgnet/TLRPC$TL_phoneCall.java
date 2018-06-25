package org.telegram.tgnet;

public class TLRPC$TL_phoneCall extends TLRPC$PhoneCall {
    public static int constructor = -1660057;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
        this.participant_id = stream.readInt32(exception);
        this.g_a_or_b = stream.readByteArray(exception);
        this.key_fingerprint = stream.readInt64(exception);
        this.protocol = TLRPC$TL_phoneCallProtocol.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.connection = TLRPC$TL_phoneConnection.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_phoneConnection object = TLRPC$TL_phoneConnection.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.alternative_connections.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.start_date = stream.readInt32(exception);
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.date);
        stream.writeInt32(this.admin_id);
        stream.writeInt32(this.participant_id);
        stream.writeByteArray(this.g_a_or_b);
        stream.writeInt64(this.key_fingerprint);
        this.protocol.serializeToStream(stream);
        this.connection.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.alternative_connections.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_phoneConnection) this.alternative_connections.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(this.start_date);
    }
}
