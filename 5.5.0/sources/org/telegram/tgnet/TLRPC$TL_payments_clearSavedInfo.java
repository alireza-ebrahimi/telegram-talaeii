package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;

public class TLRPC$TL_payments_clearSavedInfo extends TLObject {
    public static int constructor = -667062079;
    public boolean credentials;
    public int flags;
    public boolean info;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.credentials ? this.flags | 1 : this.flags & -2;
        this.flags = this.info ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
    }
}
