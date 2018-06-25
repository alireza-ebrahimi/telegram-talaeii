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

    public static TLRPC$TL_dcOption TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_dcOption result = new TLRPC$TL_dcOption();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_dcOption", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.ipv6 = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.media_only = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.tcpo_only = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.cdn = z;
        if ((this.flags & 16) == 0) {
            z2 = false;
        }
        this.isStatic = z2;
        this.id = stream.readInt32(exception);
        this.ip_address = stream.readString(exception);
        this.port = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.ipv6 ? this.flags | 1 : this.flags & -2;
        this.flags = this.media_only ? this.flags | 2 : this.flags & -3;
        this.flags = this.tcpo_only ? this.flags | 4 : this.flags & -5;
        this.flags = this.cdn ? this.flags | 8 : this.flags & -9;
        this.flags = this.isStatic ? this.flags | 16 : this.flags & -17;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        stream.writeString(this.ip_address);
        stream.writeInt32(this.port);
    }
}
