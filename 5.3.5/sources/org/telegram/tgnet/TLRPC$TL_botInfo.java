package org.telegram.tgnet;

public class TLRPC$TL_botInfo extends TLRPC$BotInfo {
    public static int constructor = -1729618630;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.description = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_botCommand object = TLRPC$TL_botCommand.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.commands.add(object);
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
        stream.writeInt32(this.user_id);
        stream.writeString(this.description);
        stream.writeInt32(481674261);
        int count = this.commands.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_botCommand) this.commands.get(a)).serializeToStream(stream);
        }
    }
}
