package org.telegram.tgnet;

public class TLRPC$TL_inputChannelEmpty extends TLRPC$InputChannel {
    public static int constructor = -292807034;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
