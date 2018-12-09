package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionLoginUnknownLocation extends MessageAction {
    public static int constructor = 1431655925;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.title = abstractSerializedData.readString(z);
        this.address = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.address);
    }
}
