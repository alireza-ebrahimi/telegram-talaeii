package org.telegram.tgnet;

public class TLRPC$TL_phone_confirmCall extends TLObject {
    public static int constructor = 788404002;
    public byte[] g_a;
    public long key_fingerprint;
    public TLRPC$TL_inputPhoneCall peer;
    public TLRPC$TL_phoneCallProtocol protocol;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_phone_phoneCall.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeByteArray(this.g_a);
        abstractSerializedData.writeInt64(this.key_fingerprint);
        this.protocol.serializeToStream(abstractSerializedData);
    }
}
