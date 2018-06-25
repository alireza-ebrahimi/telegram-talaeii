package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessagesFilter;

public class TLRPC$TL_inputMessagesFilterPhoneCalls extends MessagesFilter {
    public static int constructor = -2134272152;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.missed = (this.flags & 1) != 0;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.missed ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
    }
}
