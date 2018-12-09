package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_users_getUsers extends TLObject {
    public static int constructor = 227648840;
    public ArrayList<InputUser> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLObject tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                break;
            }
            tLRPC$Vector.objects.add(TLdeserialize);
        }
        return tLRPC$Vector;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.id.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((InputUser) this.id.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
