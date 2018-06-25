package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionDeleteMessages extends TLRPC$DecryptedMessageAction {
    public static int constructor = 1700872964;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            for (int a = 0; a < count; a++) {
                this.random_ids.add(Long.valueOf(stream.readInt64(exception)));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.random_ids.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.random_ids.get(a)).longValue());
        }
    }
}
