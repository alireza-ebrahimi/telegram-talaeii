package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeVideo_layer65 extends TLRPC$TL_documentAttributeVideo {
    public static int constructor = 1494273227;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.duration = stream.readInt32(exception);
        this.w = stream.readInt32(exception);
        this.h = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.duration);
        stream.writeInt32(this.w);
        stream.writeInt32(this.h);
    }
}
