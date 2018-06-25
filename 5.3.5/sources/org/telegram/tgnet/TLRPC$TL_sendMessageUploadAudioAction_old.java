package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadAudioAction_old extends TLRPC$TL_sendMessageUploadAudioAction {
    public static int constructor = -424899985;

    public void readParams(AbstractSerializedData stream, boolean exception) {
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
