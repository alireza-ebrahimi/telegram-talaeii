package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;
import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageEncryptedAction extends MessageAction {
    public static int constructor = 1431655927;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.encryptedAction = DecryptedMessageAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.encryptedAction.serializeToStream(abstractSerializedData);
    }
}
