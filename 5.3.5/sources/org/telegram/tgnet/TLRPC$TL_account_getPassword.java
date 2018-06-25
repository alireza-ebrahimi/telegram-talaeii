package org.telegram.tgnet;

public class TLRPC$TL_account_getPassword extends TLObject {
    public static int constructor = 1418342645;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$account_Password.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
