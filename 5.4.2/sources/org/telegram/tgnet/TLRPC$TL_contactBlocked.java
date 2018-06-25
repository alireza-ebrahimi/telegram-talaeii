package org.telegram.tgnet;

public class TLRPC$TL_contactBlocked extends TLObject {
    public static int constructor = 1444661369;
    public int date;
    public int user_id;

    public static TLRPC$TL_contactBlocked TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_contactBlocked tLRPC$TL_contactBlocked = new TLRPC$TL_contactBlocked();
            tLRPC$TL_contactBlocked.readParams(abstractSerializedData, z);
            return tLRPC$TL_contactBlocked;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contactBlocked", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt32(this.date);
    }
}
