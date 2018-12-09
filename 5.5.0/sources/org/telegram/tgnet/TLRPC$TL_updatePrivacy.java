package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PrivacyKey;
import org.telegram.tgnet.TLRPC.PrivacyRule;

public class TLRPC$TL_updatePrivacy extends TLRPC$Update {
    public static int constructor = -298113238;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.key = PrivacyKey.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                PrivacyRule TLdeserialize = PrivacyRule.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.rules.add(TLdeserialize);
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
        this.key.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.rules.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((PrivacyRule) this.rules.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
