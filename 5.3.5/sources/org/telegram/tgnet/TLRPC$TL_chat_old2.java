package org.telegram.tgnet;

public class TLRPC$TL_chat_old2 extends TLRPC$TL_chat {
    public static int constructor = 1930607688;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.creator = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.kicked = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.left = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.admins_enabled = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.admin = z;
        if ((this.flags & 32) == 0) {
            z2 = false;
        }
        this.deactivated = z2;
        this.id = stream.readInt32(exception);
        this.title = stream.readString(exception);
        this.photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.participants_count = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.version = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.creator ? this.flags | 1 : this.flags & -2;
        this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
        this.flags = this.left ? this.flags | 4 : this.flags & -5;
        this.flags = this.admins_enabled ? this.flags | 8 : this.flags & -9;
        this.flags = this.admin ? this.flags | 16 : this.flags & -17;
        this.flags = this.deactivated ? this.flags | 32 : this.flags & -33;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        stream.writeString(this.title);
        this.photo.serializeToStream(stream);
        stream.writeInt32(this.participants_count);
        stream.writeInt32(this.date);
        stream.writeInt32(this.version);
    }
}
