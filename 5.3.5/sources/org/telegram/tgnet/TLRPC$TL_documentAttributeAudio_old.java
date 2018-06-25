package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeAudio_old extends TLRPC$TL_documentAttributeAudio {
    public static int constructor = 85215461;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.duration = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.duration);
    }
}
