package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.NotifyPeer;
import org.telegram.tgnet.TLRPC.PeerNotifySettings;

public class TLRPC$TL_updateNotifySettings extends TLRPC$Update {
    public static int constructor = -1094555409;
    public NotifyPeer peer;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.peer = NotifyPeer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.notify_settings.serializeToStream(abstractSerializedData);
    }
}
