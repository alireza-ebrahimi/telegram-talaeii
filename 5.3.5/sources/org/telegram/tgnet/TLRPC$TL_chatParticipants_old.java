package org.telegram.tgnet;

public class TLRPC$TL_chatParticipants_old extends TLRPC$TL_chatParticipants {
    public static int constructor = 2017571861;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.admin_id = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$ChatParticipant object = TLRPC$ChatParticipant.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.participants.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.version = stream.readInt32(exception);
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.admin_id);
        stream.writeInt32(481674261);
        int count = this.participants.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$ChatParticipant) this.participants.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(this.version);
    }
}
