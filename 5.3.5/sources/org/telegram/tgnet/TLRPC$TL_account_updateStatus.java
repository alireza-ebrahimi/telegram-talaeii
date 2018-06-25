package org.telegram.tgnet;

public class TLRPC$TL_account_updateStatus extends TLObject {
    public static int constructor = 1713919532;
    public boolean offline;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeBool(this.offline);
    }
}
