package org.telegram.tgnet;

public class TLRPC$TL_auth_passwordRecovery extends TLObject {
    public static int constructor = 326715557;
    public String email_pattern;

    public static TLRPC$TL_auth_passwordRecovery TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_auth_passwordRecovery result = new TLRPC$TL_auth_passwordRecovery();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_auth_passwordRecovery", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.email_pattern = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.email_pattern);
    }
}
