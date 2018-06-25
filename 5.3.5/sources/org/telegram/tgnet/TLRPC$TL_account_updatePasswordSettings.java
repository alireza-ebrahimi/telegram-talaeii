package org.telegram.tgnet;

public class TLRPC$TL_account_updatePasswordSettings extends TLObject {
    public static int constructor = -92517498;
    public byte[] current_password_hash;
    public TLRPC$TL_account_passwordInputSettings new_settings;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.current_password_hash);
        this.new_settings.serializeToStream(stream);
    }
}
