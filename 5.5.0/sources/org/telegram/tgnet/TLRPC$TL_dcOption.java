package org.telegram.tgnet;

public class TLRPC$TL_dcOption extends TLObject {
    public static int constructor = 98092748;
    public boolean cdn;
    public int flags;
    public int id;
    public String ip_address;
    public boolean ipv6;
    public boolean isStatic;
    public boolean media_only;
    public int port;
    public boolean tcpo_only;

    public static TLRPC$TL_dcOption TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_dcOption tLRPC$TL_dcOption = new TLRPC$TL_dcOption();
            tLRPC$TL_dcOption.readParams(abstractSerializedData, z);
            return tLRPC$TL_dcOption;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_dcOption", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.ipv6 = (this.flags & 1) != 0;
        this.media_only = (this.flags & 2) != 0;
        this.tcpo_only = (this.flags & 4) != 0;
        this.cdn = (this.flags & 8) != 0;
        if ((this.flags & 16) == 0) {
            z2 = false;
        }
        this.isStatic = z2;
        this.id = abstractSerializedData.readInt32(z);
        this.ip_address = abstractSerializedData.readString(z);
        this.port = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.ipv6 ? this.flags | 1 : this.flags & -2;
        this.flags = this.media_only ? this.flags | 2 : this.flags & -3;
        this.flags = this.tcpo_only ? this.flags | 4 : this.flags & -5;
        this.flags = this.cdn ? this.flags | 8 : this.flags & -9;
        this.flags = this.isStatic ? this.flags | 16 : this.flags & -17;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeString(this.ip_address);
        abstractSerializedData.writeInt32(this.port);
    }
}
