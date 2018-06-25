package org.telegram.tgnet;

public class TLRPC$TL_null extends TLObject {
    public static int constructor = 1450380236;

    public static TLRPC$TL_null TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_null tLRPC$TL_null = new TLRPC$TL_null();
            tLRPC$TL_null.readParams(abstractSerializedData, z);
            return tLRPC$TL_null;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_null", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
