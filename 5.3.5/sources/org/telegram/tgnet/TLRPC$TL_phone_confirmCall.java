package org.telegram.tgnet;

public class TLRPC$TL_phone_confirmCall extends TLObject {
    public static int constructor = 788404002;
    public byte[] g_a;
    public long key_fingerprint;
    public TLRPC$TL_inputPhoneCall peer;
    public TLRPC$TL_phoneCallProtocol protocol;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_phone_phoneCall.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeByteArray(this.g_a);
        stream.writeInt64(this.key_fingerprint);
        this.protocol.serializeToStream(stream);
    }
}
