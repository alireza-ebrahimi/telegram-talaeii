package org.telegram.tgnet;

public class TLRPC$TL_peerSettings extends TLObject {
    public static int constructor = -2122045747;
    public int flags;
    public boolean report_spam;

    public static TLRPC$TL_peerSettings TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_peerSettings tLRPC$TL_peerSettings = new TLRPC$TL_peerSettings();
            tLRPC$TL_peerSettings.readParams(abstractSerializedData, z);
            return tLRPC$TL_peerSettings;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_peerSettings", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.report_spam = (this.flags & 1) != 0;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.report_spam ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
    }
}
