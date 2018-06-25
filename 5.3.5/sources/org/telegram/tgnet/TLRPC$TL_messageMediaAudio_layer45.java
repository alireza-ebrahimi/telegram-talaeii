package org.telegram.tgnet;

public class TLRPC$TL_messageMediaAudio_layer45 extends TLRPC$MessageMedia {
    public static int constructor = -961117440;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.audio_unused = TLRPC$Audio.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.audio_unused.serializeToStream(stream);
    }
}
