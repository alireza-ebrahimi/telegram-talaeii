package org.telegram.tgnet;

public class TLRPC$TL_account_deleteAccount extends TLObject {
    public static int constructor = 1099779595;
    public String reason;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.reason);
    }
}
