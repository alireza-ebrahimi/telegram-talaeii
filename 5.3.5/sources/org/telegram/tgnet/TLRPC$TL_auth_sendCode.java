package org.telegram.tgnet;

public class TLRPC$TL_auth_sendCode extends TLObject {
    public static int constructor = -2035355412;
    public boolean allow_flashcall;
    public String api_hash;
    public int api_id;
    public boolean current_number;
    public int flags;
    public String phone_number;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_sentCode.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.allow_flashcall ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeString(this.phone_number);
        if ((this.flags & 1) != 0) {
            stream.writeBool(this.current_number);
        }
        stream.writeInt32(this.api_id);
        stream.writeString(this.api_hash);
    }
}
