package org.telegram.tgnet;

public class TLRPC$TL_phone_joinGroupCall extends TLObject {
    public static int constructor = 165360343;
    public TLRPC$TL_inputGroupCall call;
    public long key_fingerprint;
    public byte[] streams;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.call.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeByteArray(this.streams);
        abstractSerializedData.writeInt64(this.key_fingerprint);
    }
}
