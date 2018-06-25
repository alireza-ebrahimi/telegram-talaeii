package org.telegram.tgnet;

public class TLRPC$TL_messageMediaVideo_layer45 extends TLRPC$MessageMedia {
    public static int constructor = 1540298357;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.video_unused = TLRPC$Video.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.caption = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.video_unused.serializeToStream(stream);
        stream.writeString(this.caption);
    }
}
