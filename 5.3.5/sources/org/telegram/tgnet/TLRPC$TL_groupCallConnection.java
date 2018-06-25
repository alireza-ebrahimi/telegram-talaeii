package org.telegram.tgnet;

public class TLRPC$TL_groupCallConnection extends TLObject {
    public static int constructor = 1081287011;
    public long id;
    public String ip;
    public String ipv6;
    public int port;

    public static TLRPC$TL_groupCallConnection TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_groupCallConnection result = new TLRPC$TL_groupCallConnection();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_groupCallConnection", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.ip = stream.readString(exception);
        this.ipv6 = stream.readString(exception);
        this.port = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeString(this.ip);
        stream.writeString(this.ipv6);
        stream.writeInt32(this.port);
    }
}
