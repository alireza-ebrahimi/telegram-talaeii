package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeAudio extends DocumentAttribute {
    public static int constructor = -1739392570;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.voice = (this.flags & 1024) != 0;
        this.duration = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.title = stream.readString(exception);
        }
        if ((this.flags & 2) != 0) {
            this.performer = stream.readString(exception);
        }
        if ((this.flags & 4) != 0) {
            this.waveform = stream.readByteArray(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.voice ? this.flags | 1024 : this.flags & -1025;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.duration);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.title);
        }
        if ((this.flags & 2) != 0) {
            stream.writeString(this.performer);
        }
        if ((this.flags & 4) != 0) {
            stream.writeByteArray(this.waveform);
        }
    }
}
