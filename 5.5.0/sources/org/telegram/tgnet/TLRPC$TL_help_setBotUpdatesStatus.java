package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;

public class TLRPC$TL_help_setBotUpdatesStatus extends TLObject {
    public static int constructor = -333262899;
    public String message;
    public int pending_updates_count;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.pending_updates_count);
        abstractSerializedData.writeString(this.message);
    }
}
