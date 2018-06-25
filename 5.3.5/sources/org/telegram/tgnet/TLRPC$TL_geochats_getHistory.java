package org.telegram.tgnet;

public class TLRPC$TL_geochats_getHistory extends TLObject {
    public static int constructor = -1254131096;
    public int limit;
    public int max_id;
    public int offset;
    public TLRPC$TL_inputGeoChat peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$geochats_Messages.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.max_id);
        stream.writeInt32(this.limit);
    }
}
