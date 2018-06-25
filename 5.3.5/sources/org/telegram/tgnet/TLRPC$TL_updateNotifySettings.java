package org.telegram.tgnet;

public class TLRPC$TL_updateNotifySettings extends TLRPC$Update {
    public static int constructor = -1094555409;
    public TLRPC$NotifyPeer peer;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.peer = TLRPC$NotifyPeer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        this.notify_settings.serializeToStream(stream);
    }
}
