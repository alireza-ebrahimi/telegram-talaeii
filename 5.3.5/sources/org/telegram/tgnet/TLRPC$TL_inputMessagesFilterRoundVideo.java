package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterRoundVideo extends TLRPC$MessagesFilter {
    public static int constructor = -1253451181;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
