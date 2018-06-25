package org.telegram.tgnet;

public class TLRPC$TL_paymentSavedCredentialsCard extends TLObject {
    public static int constructor = -842892769;
    public String id;
    public String title;

    public static TLRPC$TL_paymentSavedCredentialsCard TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_paymentSavedCredentialsCard result = new TLRPC$TL_paymentSavedCredentialsCard();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_paymentSavedCredentialsCard", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readString(exception);
        this.title = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.id);
        stream.writeString(this.title);
    }
}
