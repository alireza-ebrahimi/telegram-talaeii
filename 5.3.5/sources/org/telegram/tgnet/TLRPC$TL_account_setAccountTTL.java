package org.telegram.tgnet;

public class TLRPC$TL_account_setAccountTTL extends TLObject {
    public static int constructor = 608323678;
    public TLRPC$TL_accountDaysTTL ttl;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.ttl.serializeToStream(stream);
    }
}
