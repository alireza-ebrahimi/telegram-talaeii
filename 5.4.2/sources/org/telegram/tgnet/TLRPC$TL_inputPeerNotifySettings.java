package org.telegram.tgnet;

public class TLRPC$TL_inputPeerNotifySettings extends TLObject {
    public static int constructor = 949182130;
    public int flags;
    public int mute_until;
    public boolean show_previews;
    public boolean silent;
    public String sound;

    public static TLRPC$TL_inputPeerNotifySettings TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputPeerNotifySettings tLRPC$TL_inputPeerNotifySettings = new TLRPC$TL_inputPeerNotifySettings();
            tLRPC$TL_inputPeerNotifySettings.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputPeerNotifySettings;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputPeerNotifySettings", new Object[]{Integer.valueOf(i)}));
        }
    }

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
