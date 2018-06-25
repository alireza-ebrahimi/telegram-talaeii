package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_contacts_blockedSlice extends TLRPC$contacts_Blocked {
    public static int constructor = -1878523231;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.count = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_contactBlocked object = TLRPC$TL_contactBlocked.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.blocked.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    User object2 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.users.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(this.count);
        stream.writeInt32(481674261);
        int count = this.blocked.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_contactBlocked) this.blocked.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.users.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
