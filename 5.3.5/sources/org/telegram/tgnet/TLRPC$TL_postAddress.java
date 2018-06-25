package org.telegram.tgnet;

public class TLRPC$TL_postAddress extends TLObject {
    public static int constructor = 512535275;
    public String city;
    public String country_iso2;
    public String post_code;
    public String state;
    public String street_line1;
    public String street_line2;

    public static TLRPC$TL_postAddress TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_postAddress result = new TLRPC$TL_postAddress();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_postAddress", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.street_line1 = stream.readString(exception);
        this.street_line2 = stream.readString(exception);
        this.city = stream.readString(exception);
        this.state = stream.readString(exception);
        this.country_iso2 = stream.readString(exception);
        this.post_code = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.street_line1);
        stream.writeString(this.street_line2);
        stream.writeString(this.city);
        stream.writeString(this.state);
        stream.writeString(this.country_iso2);
        stream.writeString(this.post_code);
    }
}
