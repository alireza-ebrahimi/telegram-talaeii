package org.telegram.tgnet;

public class TLRPC$TL_inputPeerNotifySettings extends TLObject {
    public static int constructor = 949182130;
    public int flags;
    public int mute_until;
    public boolean show_previews;
    public boolean silent;
    public String sound;

    public static TLRPC$TL_inputPeerNotifySettings TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputPeerNotifySettings result = new TLRPC$TL_inputPeerNotifySettings();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputPeerNotifySettings", new Object[]{Integer.valueOf(constructor)}));
        }
    }

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
