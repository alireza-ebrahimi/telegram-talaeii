package org.telegram.tgnet;

public class TLRPC$TL_draftMessageEmpty extends TLRPC$DraftMessage {
    public static int constructor = -1169445179;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
