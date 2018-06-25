package org.telegram.tgnet;

public class TLRPC$TL_account_getPrivacy extends TLObject {
    public static int constructor = -623130288;
    public TLRPC$InputPrivacyKey key;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_account_privacyRules.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.key.serializeToStream(stream);
    }
}
