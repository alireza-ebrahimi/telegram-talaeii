package org.telegram.tgnet;

public class TLRPC$TL_peerNotifySettings extends TLRPC$PeerNotifySettings {
    public static int constructor = -1697798976;
    public boolean show_previews;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.show_previews = z;
        if ((this.flags & 2) == 0) {
            z2 = false;
        }
        this.silent = z2;
        this.mute_until = stream.readInt32(exception);
        this.sound = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.show_previews ? this.flags | 1 : this.flags & -2;
        this.flags = this.silent ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.mute_until);
        stream.writeString(this.sound);
    }
}
