package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GroupCall;

public class TLRPC$TL_updateGroupCall extends TLRPC$Update {
    public static int constructor = -2046916883;
    public GroupCall call;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.call = GroupCall.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.call.serializeToStream(abstractSerializedData);
    }
}
