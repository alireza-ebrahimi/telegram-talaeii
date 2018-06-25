package org.telegram.tgnet;

public class TLRPC$TL_account_getAccountTTL extends TLObject {
    public static int constructor = 150761757;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_accountDaysTTL.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
