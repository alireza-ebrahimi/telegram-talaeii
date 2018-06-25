package org.telegram.tgnet;

public class TLRPC$TL_account_checkUsername extends TLObject {
    public static int constructor = 655677548;
    public String username;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.username);
    }
}
