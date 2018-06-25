package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_phone_discardCall extends TLObject {
    public static int constructor = 2027164582;
    public long connection_id;
    public int duration;
    public TLRPC$TL_inputPhoneCall peer;
    public PhoneCallDiscardReason reason;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.duration);
        this.reason.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt64(this.connection_id);
    }
}
