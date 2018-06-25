package org.telegram.tgnet;

public class TLRPC$TL_phone_createGroupCall extends TLObject {
    public static int constructor = -2063276618;
    public TLRPC$InputChannel channel;
    public byte[] encryption_key;
    public int flags;
    public long key_fingerprint;
    public TLRPC$TL_phoneCallProtocol protocol;
    public int random_id;
    public byte[] streams;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.channel.serializeToStream(stream);
        stream.writeInt32(this.random_id);
        this.protocol.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeByteArray(this.encryption_key);
        }
        stream.writeInt64(this.key_fingerprint);
        stream.writeByteArray(this.streams);
    }
}
