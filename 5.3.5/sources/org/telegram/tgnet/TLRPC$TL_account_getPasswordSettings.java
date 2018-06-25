package org.telegram.tgnet;

public class TLRPC$TL_account_getPasswordSettings extends TLObject {
    public static int constructor = -1131605573;
    public byte[] current_password_hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_account_passwordSettings.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.current_password_hash);
    }
}
