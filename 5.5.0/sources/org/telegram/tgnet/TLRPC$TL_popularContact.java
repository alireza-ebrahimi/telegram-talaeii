package org.telegram.tgnet;

public class TLRPC$TL_popularContact extends TLObject {
    public static int constructor = 1558266229;
    public long client_id;
    public int importers;

    public static TLRPC$TL_popularContact TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_popularContact tLRPC$TL_popularContact = new TLRPC$TL_popularContact();
            tLRPC$TL_popularContact.readParams(abstractSerializedData, z);
            return tLRPC$TL_popularContact;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_popularContact", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.client_id = abstractSerializedData.readInt64(z);
        this.importers = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.client_id);
        abstractSerializedData.writeInt32(this.importers);
    }
}
