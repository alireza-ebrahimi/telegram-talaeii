package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCall;

public class TLRPC$TL_groupCallPrivate extends GroupCall {
    public static int constructor = 1829443076;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        if ((this.flags & 1) != 0) {
            this.channel_id = abstractSerializedData.readInt32(z);
        }
        this.participants_count = abstractSerializedData.readInt32(z);
        this.admin_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.channel_id);
        }
        abstractSerializedData.writeInt32(this.participants_count);
        abstractSerializedData.writeInt32(this.admin_id);
    }
}
