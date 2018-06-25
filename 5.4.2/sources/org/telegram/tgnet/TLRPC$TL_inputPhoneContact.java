package org.telegram.tgnet;

public class TLRPC$TL_inputPhoneContact extends TLObject {
    public static int constructor = -208488460;
    public long client_id;
    public String first_name;
    public String last_name;
    public String phone;

    public static TLRPC$TL_inputPhoneContact TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputPhoneContact tLRPC$TL_inputPhoneContact = new TLRPC$TL_inputPhoneContact();
            tLRPC$TL_inputPhoneContact.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputPhoneContact;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputPhoneContact", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.client_id = abstractSerializedData.readInt64(z);
        this.phone = abstractSerializedData.readString(z);
        this.first_name = abstractSerializedData.readString(z);
        this.last_name = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.client_id);
        abstractSerializedData.writeString(this.phone);
        abstractSerializedData.writeString(this.first_name);
        abstractSerializedData.writeString(this.last_name);
    }
}
