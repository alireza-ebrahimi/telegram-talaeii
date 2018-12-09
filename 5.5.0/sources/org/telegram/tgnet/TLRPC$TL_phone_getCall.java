package org.telegram.tgnet;

public class TLRPC$TL_phone_getCall extends TLObject {
    public static int constructor = -1965338759;
    public TLRPC$TL_inputPhoneCall peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_phone_phoneCall.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
    }
}
