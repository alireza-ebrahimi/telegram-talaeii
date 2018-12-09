package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PeerNotifySettings;

public class TLRPC$TL_peerNotifySettings extends PeerNotifySettings {
    public static int constructor = -1697798976;
    public boolean show_previews;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.show_previews = (this.flags & 1) != 0;
        if ((this.flags & 2) == 0) {
            z2 = false;
        }
        this.silent = z2;
        this.mute_until = abstractSerializedData.readInt32(z);
        this.sound = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.show_previews ? this.flags | 1 : this.flags & -2;
        this.flags = this.silent ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.mute_until);
        abstractSerializedData.writeString(this.sound);
    }
}
