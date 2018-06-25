package org.telegram.tgnet;

public class TLRPC$TL_phone_upgradePhoneCall extends TLObject {
    public static int constructor = -1729901126;
    public byte[] encryption_key;
    public int flags;
    public long key_fingerprint;
    public TLRPC$TL_inputPhoneCall peer;
    public byte[] streams;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_phone_groupCall.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeByteArray(this.encryption_key);
        }
        abstractSerializedData.writeInt64(this.key_fingerprint);
        abstractSerializedData.writeByteArray(this.streams);
    }
}
