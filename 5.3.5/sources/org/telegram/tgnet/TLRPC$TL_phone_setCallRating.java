package org.telegram.tgnet;

public class TLRPC$TL_phone_setCallRating extends TLObject {
    public static int constructor = 475228724;
    public String comment;
    public TLRPC$TL_inputPhoneCall peer;
    public int rating;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.rating);
        stream.writeString(this.comment);
    }
}
