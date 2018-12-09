package org.telegram.tgnet;

public class TLRPC$TL_payments_getSavedInfo extends TLObject {
    public static int constructor = 578650699;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_payments_savedInfo.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
