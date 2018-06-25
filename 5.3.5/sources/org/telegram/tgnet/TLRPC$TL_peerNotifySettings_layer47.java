package org.telegram.tgnet;

public class TLRPC$TL_peerNotifySettings_layer47 extends TLRPC$TL_peerNotifySettings {
    public static int constructor = -1923214866;
    public boolean show_previews;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.mute_until = stream.readInt32(exception);
        this.sound = stream.readString(exception);
        this.show_previews = stream.readBool(exception);
        this.events_mask = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.mute_until);
        stream.writeString(this.sound);
        stream.writeBool(this.show_previews);
        stream.writeInt32(this.events_mask);
    }
}
