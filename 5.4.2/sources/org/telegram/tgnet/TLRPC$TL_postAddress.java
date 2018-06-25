package org.telegram.tgnet;

public class TLRPC$TL_postAddress extends TLObject {
    public static int constructor = 512535275;
    public String city;
    public String country_iso2;
    public String post_code;
    public String state;
    public String street_line1;
    public String street_line2;

    public static TLRPC$TL_postAddress TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_postAddress tLRPC$TL_postAddress = new TLRPC$TL_postAddress();
            tLRPC$TL_postAddress.readParams(abstractSerializedData, z);
            return tLRPC$TL_postAddress;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_postAddress", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.street_line1 = abstractSerializedData.readString(z);
        this.street_line2 = abstractSerializedData.readString(z);
        this.city = abstractSerializedData.readString(z);
        this.state = abstractSerializedData.readString(z);
        this.country_iso2 = abstractSerializedData.readString(z);
        this.post_code = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.street_line1);
        abstractSerializedData.writeString(this.street_line2);
        abstractSerializedData.writeString(this.city);
        abstractSerializedData.writeString(this.state);
        abstractSerializedData.writeString(this.country_iso2);
        abstractSerializedData.writeString(this.post_code);
    }
}
