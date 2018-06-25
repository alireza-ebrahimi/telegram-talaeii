package org.telegram.tgnet;

public class TLRPC$TL_inputPrivacyValueAllowUsers extends TLRPC$InputPrivacyRule {
    public static int constructor = 320652927;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$InputUser object = TLRPC$InputUser.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.users.add(object);
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
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.users.get(a)).serializeToStream(stream);
        }
    }
}
