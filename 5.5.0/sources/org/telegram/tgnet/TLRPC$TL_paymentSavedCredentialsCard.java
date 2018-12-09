package org.telegram.tgnet;

public class TLRPC$TL_paymentSavedCredentialsCard extends TLObject {
    public static int constructor = -842892769;
    public String id;
    public String title;

    public static TLRPC$TL_paymentSavedCredentialsCard TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_paymentSavedCredentialsCard tLRPC$TL_paymentSavedCredentialsCard = new TLRPC$TL_paymentSavedCredentialsCard();
            tLRPC$TL_paymentSavedCredentialsCard.readParams(abstractSerializedData, z);
            return tLRPC$TL_paymentSavedCredentialsCard;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_paymentSavedCredentialsCard", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readString(z);
        this.title = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.id);
        abstractSerializedData.writeString(this.title);
    }
}
