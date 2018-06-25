package org.telegram.tgnet;

public abstract class TLRPC$PeerNotifySettings extends TLObject {
    public int events_mask;
    public int flags;
    public int mute_until;
    public boolean silent;
    public String sound;

    public static TLRPC$PeerNotifySettings TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PeerNotifySettings result = null;
        switch (constructor) {
            case -1923214866:
                result = new TLRPC$TL_peerNotifySettings_layer47();
                break;
            case -1697798976:
                result = new TLRPC$TL_peerNotifySettings();
                break;
            case 1889961234:
                result = new TLRPC$TL_peerNotifySettingsEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PeerNotifySettings", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
