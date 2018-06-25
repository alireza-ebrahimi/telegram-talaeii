package org.telegram.tgnet;

public class TLRPC$TL_dataJSON extends TLObject {
    public static int constructor = 2104790276;
    public String data;

    public static TLRPC$TL_dataJSON TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_dataJSON result = new TLRPC$TL_dataJSON();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_dataJSON", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.data = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.data);
    }
}
