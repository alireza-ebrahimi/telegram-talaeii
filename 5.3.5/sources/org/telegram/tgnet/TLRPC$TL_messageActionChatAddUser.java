package org.telegram.tgnet;

public class TLRPC$TL_messageActionChatAddUser extends TLRPC$MessageAction {
    public static int constructor = 1217033015;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            for (int a = 0; a < count; a++) {
                this.users.add(Integer.valueOf(stream.readInt32(exception)));
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
            stream.writeInt32(((Integer) this.users.get(a)).intValue());
        }
    }
}
