package org.telegram.tgnet;

public class TLRPC$TL_peerNotifySettings_layer47 extends TLRPC$TL_peerNotifySettings {
    public static int constructor = -1923214866;
    public boolean show_previews;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.mute_until = abstractSerializedData.readInt32(z);
        this.sound = abstractSerializedData.readString(z);
        this.show_previews = abstractSerializedData.readBool(z);
        this.events_mask = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.mute_until);
        abstractSerializedData.writeString(this.sound);
        abstractSerializedData.writeBool(this.show_previews);
        abstractSerializedData.writeInt32(this.events_mask);
    }
}
