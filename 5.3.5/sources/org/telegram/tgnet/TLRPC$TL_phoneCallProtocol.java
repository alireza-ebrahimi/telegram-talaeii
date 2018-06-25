package org.telegram.tgnet;

public class TLRPC$TL_phoneCallProtocol extends TLObject {
    public static int constructor = -1564789301;
    public int flags;
    public int max_layer;
    public int min_layer;
    public boolean udp_p2p;
    public boolean udp_reflector;

    public static TLRPC$TL_phoneCallProtocol TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_phoneCallProtocol result = new TLRPC$TL_phoneCallProtocol();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_phoneCallProtocol", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.udp_p2p = z;
        if ((this.flags & 2) == 0) {
            z2 = false;
        }
        this.udp_reflector = z2;
        this.min_layer = stream.readInt32(exception);
        this.max_layer = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.udp_p2p ? this.flags | 1 : this.flags & -2;
        this.flags = this.udp_reflector ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.min_layer);
        stream.writeInt32(this.max_layer);
    }
}
