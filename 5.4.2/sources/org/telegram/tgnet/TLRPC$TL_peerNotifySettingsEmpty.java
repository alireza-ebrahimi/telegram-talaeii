package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PeerNotifySettings;

public class TLRPC$TL_peerNotifySettingsEmpty extends PeerNotifySettings {
    public static int constructor = 1889961234;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
