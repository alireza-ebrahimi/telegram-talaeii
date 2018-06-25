package org.telegram.tgnet;

public class TLRPC$TL_labeledPrice extends TLObject {
    public static int constructor = -886477832;
    public long amount;
    public String label;

    public static TLRPC$TL_labeledPrice TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_labeledPrice result = new TLRPC$TL_labeledPrice();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_labeledPrice", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.label = stream.readString(exception);
        this.amount = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.label);
        stream.writeInt64(this.amount);
    }
}
