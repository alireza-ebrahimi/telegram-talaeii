package org.telegram.tgnet;

public class TLRPC$TL_account_passwordSettings extends TLObject {
    public static int constructor = -1212732749;
    public String email;

    public static TLRPC$TL_account_passwordSettings TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_account_passwordSettings result = new TLRPC$TL_account_passwordSettings();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_account_passwordSettings", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.email = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.email);
    }
}
