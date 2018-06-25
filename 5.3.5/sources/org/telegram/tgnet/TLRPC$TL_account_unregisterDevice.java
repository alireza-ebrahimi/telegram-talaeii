package org.telegram.tgnet;

public class TLRPC$TL_account_unregisterDevice extends TLObject {
    public static int constructor = 1707432768;
    public String token;
    public int token_type;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.token_type);
        stream.writeString(this.token);
    }
}
