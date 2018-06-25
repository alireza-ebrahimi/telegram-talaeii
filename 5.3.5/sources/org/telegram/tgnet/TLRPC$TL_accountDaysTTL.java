package org.telegram.tgnet;

public class TLRPC$TL_accountDaysTTL extends TLObject {
    public static int constructor = -1194283041;
    public int days;

    public static TLRPC$TL_accountDaysTTL TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_accountDaysTTL result = new TLRPC$TL_accountDaysTTL();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_accountDaysTTL", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.days = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.days);
    }
}
