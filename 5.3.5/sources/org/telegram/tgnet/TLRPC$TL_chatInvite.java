package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_chatInvite extends TLRPC$ChatInvite {
    public static int constructor = -613092008;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.channel = z;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.broadcast = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.isPublic = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.megagroup = z;
        this.title = stream.readString(exception);
        this.photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.participants_count = stream.readInt32(exception);
        if ((this.flags & 16) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                int a = 0;
                while (a < count) {
                    User object = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.participants.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        this.flags = this.channel ? this.flags | 1 : this.flags & -2;
        this.flags = this.broadcast ? this.flags | 2 : this.flags & -3;
        this.flags = this.isPublic ? this.flags | 4 : this.flags & -5;
        if (this.megagroup) {
            i = this.flags | 8;
        } else {
            i = this.flags & -9;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeString(this.title);
        this.photo.serializeToStream(stream);
        stream.writeInt32(this.participants_count);
        if ((this.flags & 16) != 0) {
            stream.writeInt32(481674261);
            int count = this.participants.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((User) this.participants.get(a)).serializeToStream(stream);
            }
        }
    }
}
