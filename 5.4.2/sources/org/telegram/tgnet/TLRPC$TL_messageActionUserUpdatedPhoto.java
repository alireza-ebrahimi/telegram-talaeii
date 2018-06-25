package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionUserUpdatedPhoto extends MessageAction {
    public static int constructor = 1431655761;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.newUserPhoto = TLRPC$UserProfilePhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.newUserPhoto.serializeToStream(abstractSerializedData);
    }
}
