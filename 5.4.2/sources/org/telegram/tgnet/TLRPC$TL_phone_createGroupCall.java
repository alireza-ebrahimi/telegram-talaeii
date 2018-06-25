package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChannel;

public class TLRPC$TL_phone_createGroupCall extends TLObject {
    public static int constructor = -2063276618;
    public InputChannel channel;
    public byte[] encryption_key;
    public int flags;
    public long key_fingerprint;
    public TLRPC$TL_phoneCallProtocol protocol;
    public int random_id;
    public byte[] streams;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.channel.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.random_id);
        this.protocol.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeByteArray(this.encryption_key);
        }
        abstractSerializedData.writeInt64(this.key_fingerprint);
        abstractSerializedData.writeByteArray(this.streams);
    }
}
