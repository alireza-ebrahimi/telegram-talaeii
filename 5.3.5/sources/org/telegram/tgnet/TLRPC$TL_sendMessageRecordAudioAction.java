package org.telegram.tgnet;

public class TLRPC$TL_sendMessageRecordAudioAction extends TLRPC$SendMessageAction {
    public static int constructor = -718310409;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
