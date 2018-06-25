package org.telegram.tgnet;

public class TLRPC$TL_importedContact extends TLObject {
    public static int constructor = -805141448;
    public long client_id;
    public int user_id;

    public static TLRPC$TL_importedContact TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_importedContact tLRPC$TL_importedContact = new TLRPC$TL_importedContact();
            tLRPC$TL_importedContact.readParams(abstractSerializedData, z);
            return tLRPC$TL_importedContact;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_importedContact", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
        this.client_id = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt64(this.client_id);
    }
}
