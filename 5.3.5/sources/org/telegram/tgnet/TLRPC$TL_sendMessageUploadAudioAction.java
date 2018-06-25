package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadAudioAction extends TLRPC$SendMessageAction {
    public static int constructor = -212740181;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.progress = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.progress);
    }
}
