package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeAudio extends DocumentAttribute {
    public static int constructor = -1739392570;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.voice = (this.flags & 1024) != 0;
        this.duration = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.title = abstractSerializedData.readString(z);
        }
        if ((this.flags & 2) != 0) {
            this.performer = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.waveform = abstractSerializedData.readByteArray(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.voice ? this.flags | 1024 : this.flags & -1025;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.duration);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.title);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.performer);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeByteArray(this.waveform);
        }
    }
}
