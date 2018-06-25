package org.telegram.tgnet;

public class TLRPC$TL_updatePrivacy extends TLRPC$Update {
    public static int constructor = -298113238;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.key = TLRPC$PrivacyKey.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$PrivacyRule object = TLRPC$PrivacyRule.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.rules.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.key.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.rules.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$PrivacyRule) this.rules.get(a)).serializeToStream(stream);
        }
    }
}
