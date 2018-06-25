package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputChannel;

public class TLRPC$TL_inputChannelEmpty extends InputChannel {
    public static int constructor = -292807034;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
