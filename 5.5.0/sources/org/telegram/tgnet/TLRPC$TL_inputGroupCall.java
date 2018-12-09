package org.telegram.tgnet;

public class TLRPC$TL_inputGroupCall extends TLObject {
    public static int constructor = -659913713;
    public long access_hash;
    public long id;

    public static TLRPC$TL_inputGroupCall TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputGroupCall tLRPC$TL_inputGroupCall = new TLRPC$TL_inputGroupCall();
            tLRPC$TL_inputGroupCall.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputGroupCall;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputGroupCall", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
    }
}
