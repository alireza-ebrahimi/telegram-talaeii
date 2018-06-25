package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;
import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_messageActionPhoneCall extends MessageAction {
    public static int constructor = -2132731265;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.call_id = abstractSerializedData.readInt64(z);
        if ((this.flags & 1) != 0) {
            this.reason = PhoneCallDiscardReason.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 2) != 0) {
            this.duration = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.call_id);
        if ((this.flags & 1) != 0) {
            this.reason.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.duration);
        }
    }
}
