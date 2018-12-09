package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_inputUserEmpty extends InputUser {
    public static int constructor = -1182234929;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
