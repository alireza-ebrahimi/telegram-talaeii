package org.telegram.tgnet;

public class TLRPC$TL_null extends TLObject {
    public static int constructor = 1450380236;

    public static TLRPC$TL_null TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_null result = new TLRPC$TL_null();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_null", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
