package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_deleteMessages extends TLObject {
    public static int constructor = -443640366;
    public int flags;
    public ArrayList<Integer> id = new ArrayList();
    public boolean revoke;

    public static TLRPC$TL_messages_deleteMessages TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_deleteMessages result = new TLRPC$TL_messages_deleteMessages();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_deleteMessages", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_affectedMessages.TLdeserialize(stream, constructor, exception);
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.revoke = z;
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            for (int a = 0; a < count; a++) {
                this.id.add(Integer.valueOf(stream.readInt32(exception)));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.revoke ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.id.get(a)).intValue());
        }
    }
}
