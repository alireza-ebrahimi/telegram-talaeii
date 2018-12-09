package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.PhoneCall;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_phone_phoneCall extends TLObject {
    public static int constructor = -326966976;
    public PhoneCall phone_call;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_phone_phoneCall TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_phone_phoneCall tLRPC$TL_phone_phoneCall = new TLRPC$TL_phone_phoneCall();
            tLRPC$TL_phone_phoneCall.readParams(abstractSerializedData, z);
            return tLRPC$TL_phone_phoneCall;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_phone_phoneCall", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.phone_call = PhoneCall.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.users.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.phone_call.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((User) this.users.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
