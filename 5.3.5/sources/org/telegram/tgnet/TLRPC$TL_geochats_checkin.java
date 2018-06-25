package org.telegram.tgnet;

public class TLRPC$TL_geochats_checkin extends TLObject {
    public static int constructor = 1437853947;
    public TLRPC$TL_inputGeoChat peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
