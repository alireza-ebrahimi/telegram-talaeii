package org.telegram.tgnet;

public class TLRPC$TL_phone_setCallRating extends TLObject {
    public static int constructor = 475228724;
    public String comment;
    public TLRPC$TL_inputPhoneCall peer;
    public int rating;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.rating);
        abstractSerializedData.writeString(this.comment);
    }
}
