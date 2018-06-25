package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;
import org.telegram.tgnet.TLRPC.SendMessageAction;

public class TLRPC$TL_decryptedMessageActionTyping extends DecryptedMessageAction {
    public static int constructor = -860719551;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.action = SendMessageAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.action.serializeToStream(abstractSerializedData);
    }
}
