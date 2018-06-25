package org.telegram.tgnet;

public class TLRPC$TL_groupCallConnection extends TLObject {
    public static int constructor = 1081287011;
    public long id;
    public String ip;
    public String ipv6;
    public int port;

    public static TLRPC$TL_groupCallConnection TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_groupCallConnection tLRPC$TL_groupCallConnection = new TLRPC$TL_groupCallConnection();
            tLRPC$TL_groupCallConnection.readParams(abstractSerializedData, z);
            return tLRPC$TL_groupCallConnection;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_groupCallConnection", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
        this.ip = abstractSerializedData.readString(z);
        this.ipv6 = abstractSerializedData.readString(z);
        this.port = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeString(this.ip);
        abstractSerializedData.writeString(this.ipv6);
        abstractSerializedData.writeInt32(this.port);
    }
}
