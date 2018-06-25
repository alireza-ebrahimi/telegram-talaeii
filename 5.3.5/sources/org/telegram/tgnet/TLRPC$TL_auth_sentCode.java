package org.telegram.tgnet;

public class TLRPC$TL_auth_sentCode extends TLObject {
    public static int constructor = 1577067778;
    public int flags;
    public TLRPC$auth_CodeType next_type;
    public String phone_code_hash;
    public boolean phone_registered;
    public int timeout;
    public TLRPC$auth_SentCodeType type;

    public static TLRPC$TL_auth_sentCode TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_auth_sentCode result = new TLRPC$TL_auth_sentCode();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_auth_sentCode", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.phone_registered = (this.flags & 1) != 0;
        this.type = TLRPC$auth_SentCodeType.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.phone_code_hash = stream.readString(exception);
        if ((this.flags & 2) != 0) {
            this.next_type = TLRPC$auth_CodeType.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 4) != 0) {
            this.timeout = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.phone_registered ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.type.serializeToStream(stream);
        stream.writeString(this.phone_code_hash);
        if ((this.flags & 2) != 0) {
            this.next_type.serializeToStream(stream);
        }
        if ((this.flags & 4) != 0) {
            stream.writeInt32(this.timeout);
        }
    }
}
