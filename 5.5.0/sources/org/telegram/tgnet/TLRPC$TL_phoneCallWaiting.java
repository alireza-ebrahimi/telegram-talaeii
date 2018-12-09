package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhoneCall;

public class TLRPC$TL_phoneCallWaiting extends PhoneCall {
    public static int constructor = 462375633;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.date = abstractSerializedData.readInt32(z);
        this.admin_id = abstractSerializedData.readInt32(z);
        this.participant_id = abstractSerializedData.readInt32(z);
        this.protocol = TLRPC$TL_phoneCallProtocol.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.receive_date = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeInt32(this.admin_id);
        abstractSerializedData.writeInt32(this.participant_id);
        this.protocol.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.receive_date);
        }
    }
}
