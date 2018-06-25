package org.telegram.tgnet;

public class TLRPC$TL_topPeer extends TLObject {
    public static int constructor = -305282981;
    public TLRPC$Peer peer;
    public double rating;

    public static TLRPC$TL_topPeer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_topPeer result = new TLRPC$TL_topPeer();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_topPeer", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.rating = stream.readDouble(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeDouble(this.rating);
    }
}
