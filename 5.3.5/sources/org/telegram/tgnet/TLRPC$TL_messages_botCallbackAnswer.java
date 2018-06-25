package org.telegram.tgnet;

public class TLRPC$TL_messages_botCallbackAnswer extends TLObject {
    public static int constructor = 911761060;
    public boolean alert;
    public int cache_time;
    public int flags;
    public boolean has_url;
    public String message;
    public boolean native_ui;
    public String url;

    public static TLRPC$TL_messages_botCallbackAnswer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_botCallbackAnswer result = new TLRPC$TL_messages_botCallbackAnswer();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_botCallbackAnswer", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.alert = (this.flags & 2) != 0;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.has_url = z;
        if ((this.flags & 16) == 0) {
            z2 = false;
        }
        this.native_ui = z2;
        if ((this.flags & 1) != 0) {
            this.message = stream.readString(exception);
        }
        if ((this.flags & 4) != 0) {
            this.url = stream.readString(exception);
        }
        this.cache_time = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.alert ? this.flags | 2 : this.flags & -3;
        this.flags = this.has_url ? this.flags | 8 : this.flags & -9;
        this.flags = this.native_ui ? this.flags | 16 : this.flags & -17;
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.message);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.url);
        }
        stream.writeInt32(this.cache_time);
    }
}
