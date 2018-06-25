package org.telegram.tgnet;

public class TLRPC$TL_account_reportPeer extends TLObject {
    public static int constructor = -1374118561;
    public TLRPC$InputPeer peer;
    public TLRPC$ReportReason reason;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        this.reason.serializeToStream(stream);
    }
}
