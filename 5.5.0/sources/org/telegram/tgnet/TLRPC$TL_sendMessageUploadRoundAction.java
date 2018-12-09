package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.SendMessageAction;

public class TLRPC$TL_sendMessageUploadRoundAction extends SendMessageAction {
    public static int constructor = 608050278;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.progress = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.progress);
    }
}
