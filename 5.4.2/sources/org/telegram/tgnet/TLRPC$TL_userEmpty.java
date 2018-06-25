package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_userEmpty extends User {
    public static int constructor = 537022650;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
    }
}
