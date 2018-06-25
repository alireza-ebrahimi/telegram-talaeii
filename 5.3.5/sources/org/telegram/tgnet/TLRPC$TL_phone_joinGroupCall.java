package org.telegram.tgnet;

public class TLRPC$TL_phone_joinGroupCall extends TLObject {
    public static int constructor = 165360343;
    public TLRPC$TL_inputGroupCall call;
    public long key_fingerprint;
    public byte[] streams;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.call.serializeToStream(stream);
        stream.writeByteArray(this.streams);
        stream.writeInt64(this.key_fingerprint);
    }
}
