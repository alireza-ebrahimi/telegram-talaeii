package org.telegram.tgnet;

public class TLRPC$TL_groupCall extends TLRPC$GroupCall {
    public static int constructor = 177149476;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        if ((this.flags & 1) != 0) {
            this.channel_id = stream.readInt32(exception);
        }
        this.admin_id = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            this.encryption_key = stream.readByteArray(exception);
        }
        this.key_fingerprint = stream.readInt64(exception);
        this.protocol = TLRPC$TL_phoneCallProtocol.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.connection = TLRPC$TL_groupCallConnection.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.reflector_group_tag = stream.readByteArray(exception);
        this.reflector_self_tag = stream.readByteArray(exception);
        this.reflector_self_secret = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.channel_id);
        }
        stream.writeInt32(this.admin_id);
        if ((this.flags & 2) != 0) {
            stream.writeByteArray(this.encryption_key);
        }
        stream.writeInt64(this.key_fingerprint);
        this.protocol.serializeToStream(stream);
        this.connection.serializeToStream(stream);
        stream.writeByteArray(this.reflector_group_tag);
        stream.writeByteArray(this.reflector_self_tag);
        stream.writeByteArray(this.reflector_self_secret);
    }
}
