package org.telegram.tgnet;

public class TLRPC$TL_phoneCallProtocol extends TLObject {
    public static int constructor = -1564789301;
    public int flags;
    public int max_layer;
    public int min_layer;
    public boolean udp_p2p;
    public boolean udp_reflector;

    public static TLRPC$TL_phoneCallProtocol TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_phoneCallProtocol tLRPC$TL_phoneCallProtocol = new TLRPC$TL_phoneCallProtocol();
            tLRPC$TL_phoneCallProtocol.readParams(abstractSerializedData, z);
            return tLRPC$TL_phoneCallProtocol;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_phoneCallProtocol", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.udp_p2p = (this.flags & 1) != 0;
        if ((this.flags & 2) == 0) {
            z2 = false;
        }
        this.udp_reflector = z2;
        this.min_layer = abstractSerializedData.readInt32(z);
        this.max_layer = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.udp_p2p ? this.flags | 1 : this.flags & -2;
        this.flags = this.udp_reflector ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.min_layer);
        abstractSerializedData.writeInt32(this.max_layer);
    }
}
