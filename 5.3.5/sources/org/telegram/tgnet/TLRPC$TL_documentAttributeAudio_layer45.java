package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeAudio_layer45 extends TLRPC$TL_documentAttributeAudio {
    public static int constructor = -556656416;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.duration = stream.readInt32(exception);
        this.title = stream.readString(exception);
        this.performer = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.duration);
        stream.writeString(this.title);
        stream.writeString(this.performer);
    }
}
