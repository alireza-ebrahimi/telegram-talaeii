package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_users_getUsers extends TLObject {
    public static int constructor = 227648840;
    public ArrayList<TLRPC$InputUser> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            User object = User.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (object == null) {
                break;
            }
            vector.objects.add(object);
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.id.get(a)).serializeToStream(stream);
        }
    }
}
