package org.telegram.tgnet;

public class TLRPC$TL_phone_editGroupCallMember extends TLObject {
    public static int constructor = 1181064164;
    public TLRPC$TL_inputGroupCall call;
    public int flags;
    public boolean kicked;
    public boolean readonly;
    public byte[] streams;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.readonly ? this.flags | 1 : this.flags & -2;
        this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        this.call.serializeToStream(stream);
        this.user_id.serializeToStream(stream);
        if ((this.flags & 4) != 0) {
            stream.writeByteArray(this.streams);
        }
    }
}
