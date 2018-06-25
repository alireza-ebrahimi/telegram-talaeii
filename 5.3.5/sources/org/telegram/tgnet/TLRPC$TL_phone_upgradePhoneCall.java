package org.telegram.tgnet;

public class TLRPC$TL_phone_upgradePhoneCall extends TLObject {
    public static int constructor = -1729901126;
    public byte[] encryption_key;
    public int flags;
    public long key_fingerprint;
    public TLRPC$TL_inputPhoneCall peer;
    public byte[] streams;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_phone_groupCall.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeByteArray(this.encryption_key);
        }
        stream.writeInt64(this.key_fingerprint);
        stream.writeByteArray(this.streams);
    }
}
