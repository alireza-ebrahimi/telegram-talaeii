package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCall;
import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;

public class TLRPC$TL_phoneCallDiscarded extends PhoneCall {
    public static int constructor = 1355435489;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.need_rating = (this.flags & 4) != 0;
        if ((this.flags & 8) == 0) {
            z2 = false;
        }
        this.need_debug = z2;
        this.id = abstractSerializedData.readInt64(z);
        if ((this.flags & 1) != 0) {
            this.reason = PhoneCallDiscardReason.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 2) != 0) {
            this.duration = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.need_rating ? this.flags | 4 : this.flags & -5;
        this.flags = this.need_debug ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.id);
        if ((this.flags & 1) != 0) {
            this.reason.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.duration);
        }
    }
}
