package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Audio;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaAudio_layer45 extends MessageMedia {
    public static int constructor = -961117440;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.audio_unused = Audio.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.audio_unused.serializeToStream(abstractSerializedData);
    }
}
