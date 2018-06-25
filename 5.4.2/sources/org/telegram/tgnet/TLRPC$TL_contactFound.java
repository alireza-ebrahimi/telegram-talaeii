package org.telegram.tgnet;

public class TLRPC$TL_contactFound extends TLObject {
    public static int constructor = -360210539;
    public int user_id;

    public static TLRPC$TL_contactFound TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_contactFound tLRPC$TL_contactFound = new TLRPC$TL_contactFound();
            tLRPC$TL_contactFound.readParams(abstractSerializedData, z);
            return tLRPC$TL_contactFound;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contactFound", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
    }
}
