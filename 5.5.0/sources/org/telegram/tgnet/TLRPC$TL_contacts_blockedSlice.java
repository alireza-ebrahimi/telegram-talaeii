package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_contacts_blockedSlice extends TLRPC$contacts_Blocked {
    public static int constructor = -1878523231;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.count = abstractSerializedData.readInt32(z);
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                TLRPC$TL_contactBlocked TLdeserialize = TLRPC$TL_contactBlocked.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.blocked.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                i2 = abstractSerializedData.readInt32(z);
                while (i < i2) {
                    User TLdeserialize2 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.users.add(TLdeserialize2);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.count);
        abstractSerializedData.writeInt32(481674261);
        int size = this.blocked.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((TLRPC$TL_contactBlocked) this.blocked.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.users.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            ((User) this.users.get(i2)).serializeToStream(abstractSerializedData);
            i2++;
        }
    }
}
